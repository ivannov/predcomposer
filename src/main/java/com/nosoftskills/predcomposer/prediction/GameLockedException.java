package com.nosoftskills.predcomposer.prediction;

/**
 * @author Ivan St. Ivanov
 */
public class GameLockedException extends Exception {

    private static final long serialVersionUID = 7782939840327439675L;

    public GameLockedException(String message) {
        super(message);
    }
}
