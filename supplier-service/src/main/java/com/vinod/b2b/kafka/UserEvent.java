package com.vinod.b2b.kafka;

import java.util.UUID;

import com.vinod.b2b.security.Roles;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEvent {
	private UUID userId;
	private String username;
	private Roles role;
	private Boolean isActive;
	private EventType eventType;
}
