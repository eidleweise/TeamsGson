/*
 * ---Begin Copyright Notice---
 *
 * NOTICE
 *
 * THIS SOFTWARE IS THE PROPERTY OF AND CONTAINS CONFIDENTIAL INFORMATION OF
 * INFOR AND/OR ITS AFFILIATES OR SUBSIDIARIES AND SHALL NOT BE DISCLOSED
 * WITHOUT PRIOR WRITTEN PERMISSION. LICENSED CUSTOMERS MAY COPY AND ADAPT
 * THIS SOFTWARE FOR THEIR OWN USE IN ACCORDANCE WITH THE TERMS OF THEIR
 * SOFTWARE LICENSE AGREEMENT. ALL OTHER RIGHTS RESERVED.
 *
 * (c) COPYRIGHT 2021 INFOR. ALL RIGHTS RESERVED. THE WORD AND DESIGN MARKS
 * SET FORTH HEREIN ARE TRADEMARKS AND/OR REGISTERED TRADEMARKS OF INFOR
 * AND/OR ITS AFFILIATES AND SUBSIDIARIES. ALL RIGHTS RESERVED. ALL OTHER
 * TRADEMARKS LISTED HEREIN ARE THE PROPERTY OF THEIR RESPECTIVE OWNERS.
 *
 * ---End Copyright Notice---
 */
package com.bw.teamspoc;

import java.util.Date;
import java.util.List;

/**
 * The Interface IMingleClient.
 *
 * @author bwoodward
 */
public interface IMingleClient {

	/**
	 * Post a single message.
	 *
	 * @param userDN
	 *        the userDN
	 * @param context
	 *        the context
	 */
	void postMessage(final String userDN, final Context context) throws Exception;
	
	/**
	 * Gets the tag string.
	 *
	 * @param context the context
	 * @return the tag string
	 */
	String getTagString(final Context context);
	
	/**
	 * Gets the module drillback local url.
	 *
	 * @param context the context
	 * @return the module drillback local url
	 */
	String getModuleDrillbackLocalUrl(final Context context);

	/**
	 * Context iterator for multiple messages.
	 */
	interface IContextIterator {
		/**
		 * Move to the next Ming.le context in the set. Alerts where target entity isn't available are skipped.
		 * 
		 * @return if FALSE then there is no more alerts in the set.
		 */
		boolean next();

		/**
		 * Gets the Ming.le context.
		 * 
		 * @return Ming.le context
		 */
		Context getMingleContext();
	}

	/**
	 * The Class Context.
	 */
	class Context {
		/** The worksheet name. */
		private String worksheetName;		
		/** The item name. */
		private String itemName;		
		/** The bucket name. */
		private String bucketName;		
		/** The location name. */
		private String locationName;		
		/** The user name. */
		private String userName;		
		/** The privacy level. -Required*/
		private String privacyLevel;
		/** It is the cycle period id of the LogNote. */
		private Long cyclePeriodId;
		/** It is the scenario id of the LogNote. (can be null) */
		private Long scenarioId;
		/** The log note id. */
		private Long logNoteId;
		/** It is the type of the LogNote. */
		private String typeName;
		/** It is the severity of the LogNote. */
		private String severityName;
		/** It is the summer of the LogNote, the top line header -Required.*/
		private String summary; 
		/** The details. This is the message body of the LogNote -Required.*/
		private String details;
		/** It is the author of the LogNote. */
		private String user;
		/** It is the image flag of the LogNote. */
		private Boolean hasImage;
		/** Optional, associated measure name. */
		private String measureName;
		/** the file name of the attachment. -Required if we have an attachment*/
		private String attachmentFileName;
		/** the content of the attachment. -Required if we have an attachment */
		private byte[] attachmentContent;
		/** The event ref. */
		private String eventRef;
		/** The audit log ref. */
		private String auditLogRef;
		/** The additional tags. We try to derive our tags from the cycle period etc - however additional tags can go in here. */
		private List<String> additionalTags;
		/** The user group GUIDs. */
		private List<String> userGroupGUIDs;
		/** The cycle period name. */
		private String cyclePeriodName;
		/** The cycle name. */
		private String cycleName;
		/** The scenario name. */
		private String scenarioName;
		/** The module name. */
		private String moduleName;
		/** The workflow name. */
		private String workflowName;		
		/** The cycle period start date. */
		private Date cyclePeriodStartDate;		
		/** The cycle period end date. */
		private Date cyclePeriodEndDate;		
		/** The process start date. */
		private Date processStartDate;		
		/** The process due date. */
		private Date processDueDate;		
		/** The process alert date. */
		private Date processAlertDate;		
		/** The parent work sheet name. */
		private String parentWorksheetName;
		private String parentWorkSheetDisplayName;
		/** The Planning Engine id. */
		private Long planningEngineId;
		private String base64Image;

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(final String imageUrl) {
			this.imageUrl = imageUrl;
		}

		private String imageUrl;


		public String getBase64Image() {
			return base64Image;
		}

		public void setBase64Image(final String base64Image) {
			this.base64Image = base64Image;
		}


		/**
		 * Instantiates a new context.
		 */
		public Context() {
			super();
		}

		/**
		 * Gets the parent worksheet name.
		 *
		 * @return the parent worksheet name
		 */
		public String getParentWorksheetName() {
			return parentWorksheetName;
		}

		/**
		 * Sets the parent worksheet name.
		 *
		 * @param parentWorksheetName the new parent worksheet name
		 */
		public void setParentWorksheetName(final String parentWorksheetName) {
			this.parentWorksheetName = parentWorksheetName;
		}
		
		/**
		 * Gets the parent work sheet display name.
		 *
		 * @return the parent work sheet display name
		 */
		public String getParentWorkSheetDisplayName() {
			return parentWorkSheetDisplayName;
		}
		
		/**
		 * Sets the parent work sheet display name.
		 *
		 * @param parentWorkSheetDisplayName the new parent work sheet display name
		 */
		public void setParentWorkSheetDisplayName(final String parentWorkSheetDisplayName) {
			this.parentWorkSheetDisplayName = parentWorkSheetDisplayName;
		}	
		
		/**
		 * Gets the worksheet name.
		 *
		 * @return the worksheet name
		 */
		public String getWorksheetName() {
			return worksheetName;
		}
		
		/**
		 * Sets the worksheet name.
		 *
		 * @param worksheetName the new worksheet name
		 */
		public void setWorksheetName(final String worksheetName) {
			this.worksheetName = worksheetName;
		}
		
		/**
		 * Gets the item name.
		 *
		 * @return the item name
		 */
		public String getItemName() {
			return itemName;
		}
		
		/**
		 * Sets the item name.
		 *
		 * @param itemName the new item name
		 */
		public void setItemName(final String itemName) {
			this.itemName = itemName;
		}
		
		/**
		 * Gets the bucket name.
		 *
		 * @return the bucket name
		 */
		public String getBucketName() {
			return bucketName;
		}
		
		/**
		 * Sets the bucket name.
		 *
		 * @param bucketName the new bucket name
		 */
		public void setBucketName(final String bucketName) {
			this.bucketName = bucketName;
		}
		
		/**
		 * Gets the location name.
		 *
		 * @return the location name
		 */
		public String getLocationName() {
			return locationName;
		}
		
		/**
		 * Sets the location name.
		 *
		 * @param locationName the new location name
		 */
		public void setLocationName(final String locationName) {
			this.locationName = locationName;
		}
		
		/**
		 * Gets the user name.
		 *
		 * @return the user name
		 */
		public String getUserName() {
			return userName;
		}
		
		/**
		 * Sets the user name.
		 *
		 * @param userName the new user name
		 */
		public void setUserName(final String userName) {
			this.userName = userName;
		}
		
		/**
		 * Gets the privacy level.
		 *
		 * @return the privacy level
		 */
		public String getPrivacyLevel() {
			return privacyLevel;
		}
		
		/**
		 * Sets the privacy level.
		 *
		 * @param privacyLevel the new privacy level
		 */
		public void setPrivacyLevel(final String privacyLevel) {
			this.privacyLevel = privacyLevel;
		}
		
		/**
		 * Gets the cycle period id.
		 *
		 * @return the cycle period id
		 */
		public Long getCyclePeriodId() {
			return cyclePeriodId;
		}
		
		/**
		 * Sets the cycle period id.
		 *
		 * @param cyclePeriodId the new cycle period id
		 */
		public void setCyclePeriodId(final Long cyclePeriodId) {
			this.cyclePeriodId = cyclePeriodId;
		}
		
		/**
		 * Gets the scenario id.
		 *
		 * @return the scenario id
		 */
		public Long getScenarioId() {
			return scenarioId;
		}
		
		/**
		 * Sets the scenario id.
		 *
		 * @param scenarioId the new scenario id
		 */
		public void setScenarioId(final Long scenarioId) {
			this.scenarioId = scenarioId;
		}
		
		/**
		 * Gets the log note id.
		 *
		 * @return the log note id
		 */
		public Long getLogNoteId() {
			return logNoteId;
		}

		/**
		 * Sets the log note id.
		 *
		 * @param logNoteId the new log note id
		 */
		public void setLogNoteId(final Long logNoteId) {
			this.logNoteId = logNoteId;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getTypeName() {
			return typeName;
		}
		
		/**
		 * Sets the type.
		 *
		 * @param typeName the new type name
		 */
		public void setTypeName(final String typeName) {
			this.typeName = typeName;
		}
		
		/**
		 * Gets the severity.
		 *
		 * @return the severity
		 */
		public String getSeverityName() {
			return severityName;
		}
		
		/**
		 * Sets the severity.
		 *
		 * @param severityName the new severity
		 */
		public void setSeverityName(final String severityName) {
			this.severityName = severityName;
		}
		
		/**
		 * Gets the summary.
		 *
		 * @return the summary
		 */
		public String getSummary() {
			return summary;
		}

		/**
		 * Sets the summary.
		 *
		 * @param summary the new summary
		 */
		public void setSummary(final String summary) {
			this.summary = summary;
		}

		/**
		 * Gets the details.
		 *
		 * @return the details
		 */
		public String getDetails() {
			return details;
		}
		
		/**
		 * Sets the details.
		 *
		 * @param details the new details
		 */
		public void setDetails(final String details) {
			this.details = details;
		}
		
		/**
		 * Gets the user.
		 *
		 * @return the user
		 */
		public String getUser() {
			return user;
		}
		
		/**
		 * Sets the user.
		 *
		 * @param user the new user
		 */
		public void setUser(final String user) {
			this.user = user;
		}
		
		/**
		 * Gets the checks for image.
		 *
		 * @return the checks for image
		 */
		public Boolean getHasImage() {
			return hasImage;
		}
		
		/**
		 * Sets the checks for image.
		 *
		 * @param hasImage the new checks for image
		 */
		public void setHasImage(final Boolean hasImage) {
			this.hasImage = hasImage;
		}
		
		/**
		 * Gets the attachment file name.
		 *
		 * @return the attachment file name
		 */
		public String getAttachmentFileName() {
			return attachmentFileName;
		}

		/**
		 * Sets the attachment file name.
		 *
		 * @param attachmentFileName the new attachment file name
		 */
		public void setAttachmentFileName(final String attachmentFileName) {
			this.attachmentFileName = attachmentFileName;
		}

		/**
		 * Gets the attachment content.
		 *
		 * @return the attachment content
		 */
		public byte[] getAttachmentContent() {
			return attachmentContent;
		}

		/**
		 * Sets the attachment content.
		 *
		 * @param attachmentContent the new attachment content
		 */
		public void setAttachmentContent(final byte[] attachmentContent) {
			this.attachmentContent = attachmentContent;
		}

		/**
		 * Gets the event ref.
		 *
		 * @return the event ref
		 */
		public String getEventRef() {
			return eventRef;
		}

		/**
		 * Sets the event ref.
		 *
		 * @param eventRef the new event ref
		 */
		public void setEventRef(final String eventRef) {
			this.eventRef = eventRef;
		}

		/**
		 * Gets the audit log ref.
		 *
		 * @return the audit log ref
		 */
		public String getAuditLogRef() {
			return auditLogRef;
		}

		/**
		 * Sets the audit log ref.
		 *
		 * @param auditLogRef the new audit log ref
		 */
		public void setAuditLogRef(final String auditLogRef) {
			this.auditLogRef = auditLogRef;
		}

		/**
		 * Gets the additional tags.
		 *
		 * @return the additional tags
		 */
		public List<String> getAdditionalTags() {
			return additionalTags;
		}

		/**
		 * Sets the additional tags.
		 *
		 * @param additionalTags the new additional tags
		 */
		public void setAdditionalTags(final List<String> additionalTags) {
			this.additionalTags = additionalTags;
		}

		/**
		 * Gets the measure name.
		 *
		 * @return the measure name
		 */
		public String getMeasureName() {
			return measureName;
		}
		
		/**
		 * Sets the measure name.
		 *
		 * @param measureName the new measure name
		 */
		public void setMeasureName(final String measureName) {
			this.measureName = measureName;
		}

		
		/**
		 * Gets the user group GUIDs.
		 *
		 * @return the user group GUIDs
		 */
		public List<String> getUserGroupGUIDs() {
			return userGroupGUIDs;
		}

	
		/**
		 * Sets the user group GUIDs.
		 *
		 * @param userGroupGUIDs the new user group GUIDs
		 */
		public void setUserGroupGUIDs(final List<String> userGroupGUIDs) {
			this.userGroupGUIDs = userGroupGUIDs;
		}
		
		/**
		 * Gets the cycle period name.
		 *
		 * @return the cycle period name
		 */
		public String getCyclePeriodName() {
			return cyclePeriodName;
		}

		/**
		 * Sets the cycle period name.
		 *
		 * @param cyclePeriodName the new cycle period name
		 */
		public void setCyclePeriodName(final String cyclePeriodName) {
			this.cyclePeriodName = cyclePeriodName;
		}

		/**
		 * Gets the cycle name.
		 *
		 * @return the cycle name
		 */
		public String getCycleName() {
			return cycleName;
		}

		/**
		 * Sets the cycle name.
		 *
		 * @param cycleName the new cycle name
		 */
		public void setCycleName(final String cycleName) {
			this.cycleName = cycleName;
		}

		/**
		 * Gets the scenario name.
		 *
		 * @return the scenario name
		 */
		public String getScenarioName() {
			return scenarioName;
		}

		/**
		 * Sets the scenario name.
		 *
		 * @param scenarioName the new scenario name
		 */
		public void setScenarioName(final String scenarioName) {
			this.scenarioName = scenarioName;
		}

		/**
		 * Gets the module name.
		 *
		 * @return the module name
		 */
		public String getModuleName() {
			return moduleName;
		}

		/**
		 * Sets the module name.
		 *
		 * @param moduleName the new module name
		 */
		public void setModuleName(final String moduleName) {
			this.moduleName = moduleName;
		}
		
		/**
		 * Gets the workflow name.
		 *
		 * @return the workflow name
		 */
		public String getWorkflowName() {
			return workflowName;
		}

		/**
		 * Sets the workflow name.
		 *
		 * @param workflowName the new workflow name
		 */
		public void setWorkflowName(final String workflowName) {
			this.workflowName = workflowName;
		}
		
		

		/**
		 * Gets the cycle period start date.
		 *
		 * @return the cycle period start date
		 */
		public Date getCyclePeriodStartDate() {
			return cyclePeriodStartDate;
		}

		/**
		 * Sets the cycle period start date.
		 *
		 * @param cyclePeriodStartDate the new cycle period start date
		 */
		public void setCyclePeriodStartDate(final Date cyclePeriodStartDate) {
			this.cyclePeriodStartDate = cyclePeriodStartDate;
		}

		/**
		 * Gets the cycle period end date.
		 *
		 * @return the cycle period end date
		 */
		public Date getCyclePeriodEndDate() {
			return cyclePeriodEndDate;
		}

		/**
		 * Sets the cycle period end date.
		 *
		 * @param cyclePeriodEndDate the new cycle period end date
		 */
		public void setCyclePeriodEndDate(final Date cyclePeriodEndDate) {
			this.cyclePeriodEndDate = cyclePeriodEndDate;
		}

		/**
		 * Gets the process start date.
		 *
		 * @return the process start date
		 */
		public Date getProcessStartDate() {
			return processStartDate;
		}

		/**
		 * Sets the process start date.
		 *
		 * @param processStartDate the new process start date
		 */
		public void setProcessStartDate(final Date processStartDate) {
			this.processStartDate = processStartDate;
		}

		/**
		 * Gets the process due date.
		 *
		 * @return the process due date
		 */
		public Date getProcessDueDate() {
			return processDueDate;
		}

		/**
		 * Sets the process due date.
		 *
		 * @param processDueDate the new process due date
		 */
		public void setProcessDueDate(final Date processDueDate) {
			this.processDueDate = processDueDate;
		}

		/**
		 * Gets the process alert date.
		 *
		 * @return the process alert date
		 */
		public Date getProcessAlertDate() {
			return processAlertDate;
		}

		/**
		 * Sets the process alert date.
		 *
		 * @param processAlertDate the new process alert date
		 */
		public void setProcessAlertDate(final Date processAlertDate) {
			this.processAlertDate = processAlertDate;
		}

		/**
		 * Returns Planning Engine id
		 *
		 * @return the Planning Engine id
		 */
		public Long getPlanningEngineId() {
			return planningEngineId;
		}

		/**
		 * Sets Planning Engine id
		 *
		 * @param planningEngineId Planning Engine id
		 */
		public void setPlanningEngineId(final Long planningEngineId) {
			this.planningEngineId = planningEngineId;
		}
	} //End inner class context.	

	/**
	 * The Enum paramNames.
	 */
	enum IonWorkflowParamNames {
		Cycle, CyclePeriod, CyclePeriodStartDate, ProcessStartDate, ProcessDueDate, ProcessAlertDate, CyclePeriodEndDate;
	}
}
