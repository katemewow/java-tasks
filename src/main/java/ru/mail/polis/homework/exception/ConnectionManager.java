package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots;

    public ConnectionManager(List<Robot> robots) {
        this.robots = new HashMap<>();
        for (Robot robot : robots) {
            this.robots.put(robot.getRobotId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("The robot with this id was not found");
        }
        return new Connection(robot);
    }
}
