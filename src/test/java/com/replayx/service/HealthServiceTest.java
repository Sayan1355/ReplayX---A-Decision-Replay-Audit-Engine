package com.replayx.service;

import com.replayx.dto.HealthResponseDto;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthServiceTest {

    @Test
    void shouldReturnUpWhenDbConnectionIsValid() {
        Connection mockConnection = createProxyConnection(true, false);
        DataSource testDataSource = new TestDataSource(mockConnection);
        HealthService healthService = new HealthService(testDataSource);

        HealthResponseDto response = healthService.getHealthStatus();

        assertEquals("UP", response.getStatus());
        assertEquals("ReplayX", response.getService());
    }

    @Test
    void shouldReturnDownWhenDbConnectionFails() {
        DataSource testDataSource = new TestDataSource(true);
        HealthService healthService = new HealthService(testDataSource);

        HealthResponseDto response = healthService.getHealthStatus();

        assertEquals("DOWN", response.getStatus());
        assertEquals("ReplayX", response.getService());
    }

    private Connection createProxyConnection(boolean isValid, boolean isClosed) {
        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> {
                    if ("isValid".equals(method.getName())) {
                        return isValid;
                    }
                    if ("isClosed".equals(method.getName())) {
                        return isClosed;
                    }
                    if ("close".equals(method.getName())) {
                        return null;
                    }
                    return null;
                }
        );
    }

    private static class TestDataSource implements DataSource {
        private final Connection connection;
        private final boolean shouldThrow;

        TestDataSource(Connection connection) {
            this.connection = connection;
            this.shouldThrow = false;
        }

        TestDataSource(boolean shouldThrow) {
            this.connection = null;
            this.shouldThrow = shouldThrow;
        }

        @Override
        public Connection getConnection() throws SQLException {
            if (shouldThrow) {
                throw new SQLException("Connection failed");
            }
            return connection;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return getConnection();
        }

        @Override
        public PrintWriter getLogWriter() {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) {
        }

        @Override
        public void setLoginTimeout(int seconds) {
        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public Logger getParentLogger() {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) {
            return false;
        }
    }
}
