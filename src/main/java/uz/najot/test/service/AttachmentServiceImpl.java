package uz.najot.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.Attachment;
import uz.najot.test.entity.Users;
import uz.najot.test.model.FileDTO;
import uz.najot.test.repository.AttachmentRepository;
import uz.najot.test.repository.UserRepository;
import uz.najot.test.service.AttachmentService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:11 PM 34 $
 * @author: Qudratjon Komilov
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableCaching
public class AttachmentServiceImpl implements AttachmentService {

    private final UserRepository usersRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public List<FileDTO> upload(MultipartHttpServletRequest request, PrincipleUser principleUser) {
        Iterator<String> fileNames = request.getFileNames();

        Calendar calendar = Calendar.getInstance();
        File folder = new File("/uploads/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        if (!folder.exists()) {
            folder.mkdirs();
        }

        List<FileDTO> fileDTOList = new ArrayList<>();
        String username = principleUser.getUsername();
        Optional<Users> byUsername = usersRepository.findByUsername(username);


        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            String serverFileName = UUID.randomUUID().toString();
            MultipartFile file = request.getFile(fileName);
            String extension = getExtension(file.getOriginalFilename());
            File uploadFile = new File(folder.getAbsolutePath() + "/" + serverFileName + extension);

            try {
                file.transferTo(uploadFile);
                Attachment attachment = new Attachment();
                attachment.setName(file.getName());
                attachment.setPath(uploadFile.getAbsolutePath());
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());
                attachment.setExtension(extension);
                attachment.setUsers(byUsername.orElse(null));
                Attachment save = attachmentRepository.save(attachment);
                fileDTOList.add(FileDTO.builder()
                        .id(save.getId())
                        .size(save.getSize())
                        .contentType(save.getContentType())
                        .extension(save.getExtension())
                        .name(save.getName())
                        .url(ServletUriComponentsBuilder.fromCurrentContextPath().path("/attachment/download/" + save.getId()).toUriString())
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            log.info("File name: {}", fileName);
        }


        return fileDTOList;
    }

    @Override
    public HttpEntity<?> download(Long id) {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            try {
                return ResponseEntity.ok()
                        .header("Content-Type", attachment.getContentType())
                        .header("Content-Disposition", "inline; filename=" + attachment.getName() + attachment.getExtension())
                        .body(Files.readAllBytes(new File(attachment.getPath()).toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
