package org.telegram.betTest;

public interface ByuCoinCallback {
    void success(String coinAmount);
    void error(String error);
}
