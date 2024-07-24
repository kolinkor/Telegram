package org.telegram.betTest;

public interface HttpCallback {
    void onSuccess(String result);
    void onError(Exception e);
}
