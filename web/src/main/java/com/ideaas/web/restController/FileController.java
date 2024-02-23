package com.ideaas.web.restController;

import com.ideaas.services.payload.UploadFileResponse;
import com.ideaas.services.service.interfaces.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by federicoberon on 30/11/2019.
 */
//NO LONGER USED
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String idEmpresa, @RequestParam String idUbicacion) {
        String fileName = fileStorageService.storeFile(file, idEmpresa, idUbicacion);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/uploadMultipleFiles", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                        @RequestParam("idEmpresa") String idEmpresa,
                                                        @RequestParam("idUbicacion") String idUbicacion) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, idEmpresa, idUbicacion))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/deleteFile" , method = RequestMethod.POST)
    public ResponseEntity deleteFile(@RequestParam String idEmpresa , @RequestParam String fileName) {
        fileStorageService.delete(idEmpresa , fileName);

        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/downloadFile/{folder}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String folder,  @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(folder.concat(File.separator).concat(fileName));

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}