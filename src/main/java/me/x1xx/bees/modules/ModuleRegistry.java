package me.x1xx.bees.modules;

import me.x1xx.bees.modules.overlay.Overlay;
import me.x1xx.bees.modules.rolemention.RoleMention;

import java.util.*;

public class ModuleRegistry {
    private ModuleRegistry() {
    }


    protected static final Set<Class<? extends Module>> moduleSet;


    static {
        moduleSet = new HashSet<>(
                Arrays.asList(
                     Overlay.class
//                     RoleMention.class
                )
        );
    }
}
