package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.model.CommonProblem;
import java.io.File;
import io.swagger.client.model.ObjectId;
import java.util.UUID;
import io.swagger.client.model.ViolationProblem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AttachmentsApi
 */
public class AttachmentsApiTest {

    private AttachmentsApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(AttachmentsApi.class);
    }

    /**
     * Attachment download
     *
     * 
     */
    @Test
    public void downloadTest() {
        UUID attachmentId = null;
        // Void response = api.download(attachmentId);

        // TODO: test validations
    }
    /**
     * Attachment upload
     *
     * 
     */
    @Test
    public void uploadTest() {
        File file = null;
        // ObjectId response = api.upload(file);

        // TODO: test validations
    }
}
