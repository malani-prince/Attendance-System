package com.example.attendancesystem

class Constants {
    
    //API KEY
    object Api {
        
        const val X_API_KEY = "X-Api-Key"
        const val AUTHORIZATION = "Authorization"
        
    }
    
    object FundsDB {
        
        //DATABASE DETAILS
        const val DATABASE_NAME = "funds.db"
        const val DATABASE_VERSION = 1
        
        // TABLE DETAILS
        const val TABLE_NAME = "funds_table"
        const val COLUMN_ID = "_id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
    }
    
    object Preference {
        
        const val PREFERENCE_NAME = "funds"
        const val IS_FROM = "isFrom"
        const val VERSION = "version"
        const val TOKEN = "token"
        const val LOGIN_CODE = "loginCode"
        const val USER = "user"
        
    }
    
    object FilterForMoneyExpended {
        
        const val ALL = "All"
        const val TODAY = "Today"
        const val YESTERDAY = "Yesterday"
        const val THIS_WEEK = "This Week"
        const val THIS_MONTH = "This Month"
        const val LAST_MONTH = "Last Month"
        const val THIS_YEAR = "This Year"
    }
    
    object FilterForMoneyAdded {
        
        const val ALL = "All"
        const val TODAY = "Today"
        const val LAST_WEEK = "Last Week"
        const val LAST_FORTNIGHT = "Last 15 Days"
        const val THIS_MONTH = "This Month"
        const val LAST_MONTH = "Last Month"
        const val THIS_YEAR = "This Year"
    }
    
    object Sorting {
        
        const val ASCENDING = "Ascending"
        const val DESCENDING = "Descending"
        const val DATE_NEWEST = "Date (Newest First)"
        const val DATE_OLDEST = "Date (Oldest First)"
    }
    
    object DateFormat {
        
        const val YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss"
        const val DDMMYYYY_HHMMSS = "dd/MM/yyyy HH:mm:ss"
        const val YYYYMMDD_T_HHMMSS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val MMDDYYYY = "MM-dd-yyyy"
        
    }
    
    object ExtraKey {
        
        const val CUSTOMER_LIST_DATA = "customerListData"
        const val ATTACHMENT_PATH = "attachmentsPath"
        const val ATTACHMENT_TYPE = "attachmentsType"
        const val ATTACHMENT_KEY = "attachment_key"
        const val VEHICLE_INFO = "vehicleInfo"
        const val PUBLIC_UPLOAD = "public/uploads"
        const val PACKAGE = "package"
        const val IMAGE_CAPTURE_INTENT = "android.media.action.IMAGE_CAPTURE"
    }
    
}
