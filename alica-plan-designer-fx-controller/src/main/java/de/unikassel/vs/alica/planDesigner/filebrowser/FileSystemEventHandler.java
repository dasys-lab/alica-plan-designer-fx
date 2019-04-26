package de.unikassel.vs.alica.planDesigner.filebrowser;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.configuration.Configuration;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.controller.Controller;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Extensions;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.view.Types;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;


public class FileSystemEventHandler implements Runnable  {
    private final Map<WatchKey, Path> keys = new HashMap<>();
    private boolean trace = false;
    private WatchService watcher;
    private Controller controller;

    public FileSystemEventHandler(Controller controller) {
        this.controller = controller;
    }


    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("FileSystemEventHandler: register: %s%n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("FileSystemEventHandler: update: %s -> %s%n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path path) throws IOException {
        // register directory and sub-directories
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    @Override
    public void run() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            Configuration conf = ConfigurationManager.getInstance().getActiveConfiguration();
            if (conf != null) {
                if(conf.getPlansPath() != null && !conf.getPlansPath().isEmpty()) {
                    registerAll(new File(conf.getPlansPath()).toPath());
                }
                if(conf.getRolesPath() != null && !conf.getRolesPath().isEmpty()) {
                    registerAll(new File(conf.getRolesPath()).toPath());
                }
                if(conf.getTasksPath() != null && !conf.getTasksPath().isEmpty()) {
                    registerAll(new File(conf.getTasksPath()).toPath());
                }
            }
            this.trace = true;
            while (PlanDesignerApplication.isRunning()) {
                // wait for key to be signalled
                WatchKey key;
                try {
                    key = watcher.poll(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException x) {
                    return;
                }

                Path dir = keys.get(key);
                if (dir == null) {
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();

                    // TBD - provide example of how OVERFLOW event is handled
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    // Context for directory entry event is the file name of entry
                    WatchEvent<Path> ev = cast(event);
                    Path name = ev.context();
                    Path child = dir.resolve(name);

                    // print out event
                    System.out.format("FileSystemEventHandler: %s: %s%n", event.kind().name(), child);

                    // if directory is created, and watching recursively, then
                    // register it and its sub-directories
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        try {
                            if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                                registerAll(child);
                            }
                        } catch (IOException x) {
                            throw new RuntimeException(x);
                        }
                    }
                    controller.handleFileSystemEvent(event, child);
                }

                // reset key and remove from set if directory no longer accessible
                boolean valid = key.reset();
                if (!valid) {
                    keys.remove(key);

                    // all directories are inaccessible
                    if (keys.isEmpty()) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
