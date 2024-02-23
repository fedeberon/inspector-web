package com.ideaas.services.service;


import com.ideaas.services.bean.FileStorageProperties;
import com.ideaas.services.bean.Image;
import com.ideaas.services.exception.FileStorageException;
import com.ideaas.services.exception.MyFileNotFoundException;
import com.ideaas.services.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by federicoberon on 30/11/2019.
 */
//NO LONGER USED
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Value("${folderImage}")
    private String folderImage;

    @Value("${urlFileServer}")
    private String urlFileServer;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {

        // Verify that fileStorageProperties.getUploadDir() is not null
        if (fileStorageProperties == null || fileStorageProperties.getUploadDir() == null) {
            throw new IllegalArgumentException("Upload directory is null in FileStorageProperties");
        }

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public List<Image> readFiles(Long idEmpresa, Long idUbicacion) {

        final List<Image> images = new ArrayList();
        String path = urlFileServer.concat(idEmpresa.toString());   

        File urlFile = new File(path);
        if (!urlFile.exists()) {
            urlFile.mkdir();
        }

        try {
            Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try {
                        String url = folderImage.concat(idEmpresa.toString()).concat(File.separator).concat(file.getFileName().toString());
                        String nameImage = file.getFileName().toString().substring(0, file.getFileName().toString().indexOf("."));
                        String extension = file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".") + 1);

                        if (file.getFileName().toString().contains("(")) {
                            nameImage = file.getFileName().toString().substring(0, file.getFileName().toString().indexOf("("));
                        }

                        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("api/fotos_map/downloadFile/")
                        .path(idEmpresa.toString().concat("//").concat(file.getFileName().toString()))
                        .toUriString();

                        images.add(new Image(url, nameImage, extension ,fileDownloadUri, false));
                    } finally {
                        return FileVisitResult.CONTINUE;
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Image> imageOfUbicacion = new ArrayList<>();
        images.forEach(image -> {
            if (image.getName().equals(idUbicacion.toString())) {
                imageOfUbicacion.add(image);
            }
        });

        return imageOfUbicacion;
    }

    public String storeFile(MultipartFile file, String folder, String fileName) {
        // Normalize file name
        /*String fileName = StringUtils.cleanPath(file.getOriginalFilename());*/
        String path = folderImage.concat(folder);
        File urlFile = new File(path);
        if (!urlFile.exists()) {
            urlFile.mkdir();
        }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            fileName = resolveFileName(file, folder, fileName);

            Path targetLocation = this.fileStorageLocation.resolve(folder.concat("//").concat(fileName));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


    @Override
    public void delete(String idEmpresa , String fileName) {
        Path fileToDeletePath = this.fileStorageLocation.resolve(idEmpresa.concat("//").concat(fileName));
        try {
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private String resolveFileName(MultipartFile file, String folder, String fileNamme) throws IOException {
    int i = 1 ;
    String fileName;
    File newFile;
    String extension = getFileExtension(file);

    do {
        String newFileName = fileNamme;
        fileName = fileStorageLocation.toAbsolutePath().toString().concat(File.separator).concat(folder).concat(File.separator).concat(newFileName).concat("(" + i +")").concat(".").concat(extension);
        newFile = new File(fileName);
        i++;
    }
    while(newFile.getAbsoluteFile().exists());

    return newFile.getName();
}

}
