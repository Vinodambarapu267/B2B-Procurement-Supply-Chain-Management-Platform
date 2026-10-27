package com.vinod.b2b.security;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum Roles {

	ADMIN(EnumSet.allOf(Permission.class)),

	EMPLOYEE(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			Permission.PURCHASE_REQUEST_CREATE, 
			Permission.PURCHASE_REQUEST_UPDATE_DRAFT,
			Permission.PURCHASE_REQUEST_SUBMIT, 
			Permission.PURCHASE_REQUEST_READ_OWN,
			Permission.PURCHASE_REQUEST_CANCEL_OWN, 
			Permission.APPROVAL_HISTORY_READ_OWN
	)),

	MANAGER(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			Permission.PURCHASE_REQUEST_CREATE, 
			Permission.PURCHASE_REQUEST_UPDATE_DRAFT,
			Permission.PURCHASE_REQUEST_SUBMIT, 
			Permission.PURCHASE_REQUEST_READ_OWN, 
			Permission.PURCHASE_REQUEST_READ,
			Permission.PURCHASE_REQUEST_CANCEL_OWN,
			Permission.APPROVAL_PENDING_LIST, 
			Permission.APPROVAL_DECIDE, 
			Permission.APPROVAL_HISTORY_READ,
			Permission.APPROVAL_HISTORY_READ_OWN
	)),

	FINANCE(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF, 
			Permission.PURCHASE_REQUEST_READ,
			Permission.PURCHASE_REQUEST_READ_OWN,
			Permission.APPROVAL_PENDING_LIST, 
			Permission.APPROVAL_DECIDE, 
			Permission.APPROVAL_HISTORY_READ,
			Permission.APPROVAL_HISTORY_READ_OWN,
			Permission.PROCUREMENT_DASHBOARD_VIEW, 
			Permission.REPORT_SPEND_VIEW,
			Permission.REPORT_APPROVAL_CYCLE_TIME_VIEW, 
			Permission.REPORT_PURCHASE_REQUEST_STATUS_VIEW

	)),

	PROCUREMENT_OFFICER(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			Permission.PURCHASE_REQUEST_READ, 
			Permission.PURCHASE_REQUEST_READ_OWN,
			Permission.APPROVAL_PENDING_LIST, 
			Permission.APPROVAL_DECIDE, 
			Permission.APPROVAL_HISTORY_READ,
			Permission.APPROVAL_HISTORY_READ_OWN,
			Permission.PURCHASE_ORDER_READ,
			Permission.PURCHASE_ORDER_CREATE, 
			Permission.PURCHASE_ORDER_SEND, 
			Permission.PURCHASE_ORDER_COMPLETE,
			Permission.SUPPLIER_CREATE, 
			Permission.SUPPLIER_UPDATE, 
			Permission.SUPPLIER_DEACTIVATE,
			Permission.PROCUREMENT_DASHBOARD_VIEW, 
			Permission.REPORT_SPEND_VIEW,
			Permission.REPORT_SUPPLIER_PERFORMANCE_VIEW, 
			Permission.REPORT_APPROVAL_CYCLE_TIME_VIEW,
			Permission.REPORT_INVENTORY_VIEW, 
			Permission.REPORT_PURCHASE_REQUEST_STATUS_VIEW

	)),

	WAREHOUSE_MANAGER(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			Permission.PURCHASE_ORDER_READ_ASSIGNED, 
			Permission.PURCHASE_ORDER_COMPLETE,
			Permission.INVENTORY_ITEM_CREATE, 
			Permission.INVENTORY_ITEM_READ, 
			Permission.GOODS_RECEIPT_CREATE,
			Permission.INVENTORY_DISCREPANCY_REVIEW,
			Permission.REPORT_INVENTORY_VIEW
	
	)),
	
	SUPPLIER(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			
			Permission.SUPPLIER_PROFILE_READ_OWN, 
			Permission.SUPPLIER_PROFILE_UPDATE_OWN,
			Permission.PURCHASE_ORDER_READ_ASSIGNED,
			Permission.PURCHASE_ORDER_SUPPLIER_ACTION

	)), 
	
	AUDITOR(Set.of(
			Permission.USER_READ_SELF, 
			Permission.USER_UPDATE_SELF,
			Permission.PURCHASE_REQUEST_READ, 
			Permission.PURCHASE_REQUEST_READ_OWN,
			Permission.PURCHASE_ORDER_READ,
			Permission.APPROVAL_HISTORY_READ, 
			Permission.APPROVAL_HISTORY_READ_OWN,
			Permission.REPORT_SPEND_VIEW, 
			Permission.REPORT_SUPPLIER_PERFORMANCE_VIEW,
			Permission.REPORT_APPROVAL_CYCLE_TIME_VIEW, 
			Permission.REPORT_PURCHASE_REQUEST_STATUS_VIEW,
			Permission.AUDIT_LOG_READ
	));

	private final Set<Permission> permissions;

	Roles(Set<Permission> permissions) {
		this.permissions = Collections.unmodifiableSet(permissions);
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

}