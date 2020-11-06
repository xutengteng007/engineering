package com.ls.graver.context;

public class EnableContext {

    private boolean objectExtended = false;

    public boolean isObjectExtended() {
        return objectExtended;
    }

    public void extendObjectCompleted() {
        objectExtended = true;
    }
}
