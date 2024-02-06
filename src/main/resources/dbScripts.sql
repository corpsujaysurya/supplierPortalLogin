CREATE TABLE `provisional_credentials` (
  `supplierid` varchar(255) NOT NULL,
  `temppwd` varchar(255) DEFAULT NULL,
  `activeflag` varchar(5) DEFAULT NULL,
  `MESSAGE` varchar(255) DEFAULT NULL,
  `newPwdSetFlag` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`supplierid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
SELECT * FROM supplier_portal.supplier_onboarding;


CREATE TABLE `login_credentials` (
  `supplierid` varchar(255) NOT NULL,
  `passcode` varchar(255) DEFAULT NULL,
  `registrationid` varchar(255) DEFAULT NULL,
  `registrationstatus` varchar(20) DEFAULT NULL,
  `suppliername` varchar(255) DEFAULT NULL,
  `supplieremail` varchar(255) DEFAULT NULL,
  `landlineNum` bigint DEFAULT NULL,
  `mobilenum` bigint DEFAULT NULL,
  `TEMP_OTP` mediumtext,
  `ONBOARDINGSTATUS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplierid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `supplier_onboarding` (
  `company_name` varchar(255) NOT NULL,
  `company_contact_no` varchar(255) NOT NULL,
  `gstin` varchar(255) NOT NULL,
  `tin` varchar(255) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `registered_addr` varchar(255) NOT NULL,
  `correspondance_addr` varchar(255) NOT NULL,
  `spoc_name` varchar(255) NOT NULL,
  `spoc_title` varchar(255) NOT NULL,
  `spoc_contact` varchar(255) NOT NULL,
  `spoc_email` varchar(255) NOT NULL,
  `license_doc` varchar(255) NOT NULL,
  `insurance_doc` varchar(255) NOT NULL,
  `regulatory_doc` varchar(255) NOT NULL,
  `litigation_doc` varchar(255) NOT NULL,
  `credit_info_doc` varchar(255) NOT NULL,
  `nda_doc` varchar(255) NOT NULL,
  `sustainability_doc` varchar(255) NOT NULL,
  `business_licensing_doc` varchar(255) NOT NULL,
  `cancelled_cheque_doc` varchar(255) NOT NULL,
  `subcontractor_info_doc` varchar(255) NOT NULL,
  `IFSC_CODE` varchar(255) NOT NULL,
  `ACCOUNT_NUMBER` varchar(255) NOT NULL,
  `BANK_NAME` varchar(255) NOT NULL,
  `CITY` varchar(255) NOT NULL,
  `STATE` varchar(255) NOT NULL,
  `PAYMENT_MODE` varchar(255) NOT NULL,
  `BRANCH_NAME` varchar(255) NOT NULL,
  `onboarding_status` varchar(255) NOT NULL,
  `regsitration_id` varchar(255) NOT NULL,
  `regsitration_pwd` varchar(255) NOT NULL,
  PRIMARY KEY (`regsitration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
