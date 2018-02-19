package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import io.swagger.client.model.CommonProblem;
import java.io.File;
import io.swagger.client.model.ObjectId;
import java.util.UUID;
import io.swagger.client.model.ViolationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AttachmentsApi {
  /**
   * Attachment download
   * 
   * @param attachmentId Attachment id (required)
   * @return Call&lt;Void&gt;
   */
  @GET("attachments/{attachmentId}")
  Call<Void> download(
    @retrofit2.http.Path("attachmentId") UUID attachmentId
  );

  /**
   * Attachment upload
   * 
   * @param file file (required)
   * @return Call&lt;ObjectId&gt;
   */
  @retrofit2.http.Multipart
  @POST("attachments")
  Call<ObjectId> upload(
    @retrofit2.http.Part("file\"; filename=\"file") RequestBody file
  );

}
