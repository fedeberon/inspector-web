package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by federicoberon on 30/11/2019.
 */
//NO LONGER USED
public interface FileStorageService {

    List<Image> readFiles(Long idEmpresa , Long idUbicacion);

    String storeFile(MultipartFile file, String foldePath, String newName);

    Resource loadFileAsResource(String fileName);

    void delete(String idEmpresa , String fileName);
}
