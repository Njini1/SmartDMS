package com.company.eduboard.global.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer(
                "-tcp",                // TCP 모드로 실행
                "-tcpAllowOthers",     // 외부 접속 허용
                "-tcpPort", "9092"     // 포트 설정 (IntelliJ에서 접속 시 사용)
        );
    }
}
