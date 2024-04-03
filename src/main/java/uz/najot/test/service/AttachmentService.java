package uz.najot.test.service;

import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.Attachment;
import uz.najot.test.model.*;

import java.util.List;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:11 PM 34 $
 * @author: Qudratjon Komilov
 */

public interface AttachmentService {
    List<FileDTO> upload(MultipartHttpServletRequest request, PrincipleUser principleUser);

    HttpEntity<?> download(Long id);

}
