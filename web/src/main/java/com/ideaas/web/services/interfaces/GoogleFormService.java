package com.ideaas.web.services.interfaces;

import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleFormService {

//    List<File> findAllFormsFromGoogleDrive() throws IOException, GeneralSecurityException;

    void main() throws IOException, GeneralSecurityException;

    void executeCreateForm() throws IOException, GeneralSecurityException;

}
