# AttachmentsApi

All URIs are relative to *https://test.vozilla.pl/api-client-mobile*

Method | HTTP request | Description
------------- | ------------- | -------------
[**download**](AttachmentsApi.md#download) | **GET** attachments/{attachmentId} | Attachment download
[**upload**](AttachmentsApi.md#upload) | **POST** attachments | Attachment upload


<a name="download"></a>
# **download**
> Void download(attachmentId)

Attachment download



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AttachmentsApi;


AttachmentsApi apiInstance = new AttachmentsApi();
UUID attachmentId = new UUID(); // UUID | Attachment id
try {
    Void result = apiInstance.download(attachmentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentsApi#download");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **attachmentId** | [**UUID**](.md)| Attachment id |

### Return type

[**Void**](.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="upload"></a>
# **upload**
> ObjectId upload(file)

Attachment upload



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AttachmentsApi;


AttachmentsApi apiInstance = new AttachmentsApi();
File file = new File("/path/to/file.txt"); // File | file
try {
    ObjectId result = apiInstance.upload(file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentsApi#upload");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **File**| file |

### Return type

[**ObjectId**](ObjectId.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json;charset=UTF-8

