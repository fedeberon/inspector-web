package com.ideaas.api.restController;

import com.ideaas.services.bean.Image;
import com.ideaas.services.dao.MapUbicacionDao;
import com.ideaas.services.domain.MapUbicacion;
import com.ideaas.services.payload.UploadFileResponse;
import com.ideaas.services.service.interfaces.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

//NO LONGER USED
@RestController
@RequestMapping("api/fotos_map/")
@CrossOrigin(origins = "http://localhost:8082")
public class FileRestController {

    private FileStorageService fileStorageService;

    private MapUbicacionDao mapUbicacionDao;

    private static final Logger logger = LoggerFactory.getLogger(FileRestController.class);

    @Value("${folderImage}")
    private String folderImage;

    @Value("${urlFileServer}")
    private String urlFileServer;

    public FileRestController(FileStorageService fileStorageService, MapUbicacionDao mapUbicacionDao) {
        this.fileStorageService = fileStorageService;
        this.mapUbicacionDao = mapUbicacionDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "setImagesUbicacion/{id}", method = RequestMethod.GET)
    public MapUbicacion setImagesUbicacion(@PathVariable Long id) {
        MapUbicacion ubicacion = mapUbicacionDao.findById(id).orElse(null);
        if(ubicacion != null){ubicacion.setImages(fileStorageService.readFiles(ubicacion.getMapEmpresa().getId() , ubicacion.getId()));}

        return ubicacion;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{idEmpresa}/{idUbicacion}", method = RequestMethod.GET)
    public List<Image> readFiles(@PathVariable Long idEmpresa, @PathVariable Long idUbicacion){
        return fileStorageService.readFiles(idEmpresa , idUbicacion);
    }

    @RequestMapping(value = "uploadFile" , method = RequestMethod.POST , consumes = "multipart/form-data")
    public UploadFileResponse uploadFile(@RequestParam MultipartFile file, @RequestParam String idEmpresa, @RequestParam String idUbicacion) {
        String fileName = fileStorageService.storeFile(file, idEmpresa, idUbicacion);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/fotos_map/downloadFile/")
                .path(idEmpresa.concat("//").concat(fileName))
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.POST, consumes = "multipart/form-data")
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

    @RequestMapping(value = "/downloadFile/{folder}/{fileName:.+}" , method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String folder, @PathVariable String fileName, HttpServletRequest request) {
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
