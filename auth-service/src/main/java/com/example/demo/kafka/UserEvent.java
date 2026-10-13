package com.example.demo.kafka;

import java.util.UUID;

import com.example.demo.utility.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
	private UUID userId;
	private String username;
	private Roles role;
	private Boolean isActive;
	private EventType eventType;
}
