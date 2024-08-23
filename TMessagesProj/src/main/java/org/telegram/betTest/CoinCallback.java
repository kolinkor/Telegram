package org.telegram.betTest;

public interface CoinCallback {
    void success(String coinUrl);
    void error(String error);
}
