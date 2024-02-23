package com.ideaas.web.controller;

import com.ideaas.services.bean.AESPasswordEncoder;
import com.ideaas.services.bean.Email;
import com.ideaas.services.domain.Usuario;
import com.ideaas.services.service.interfaces.EmailService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailController {

    private UsuarioService usuarioService;
    private EmailService emailService;
    private AESPasswordEncoder encoder;
    private VelocityEngine velocityEngine;

    public EmailController(UsuarioService usuarioService, EmailService emailService, VelocityEngine velocityEngine) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.encoder = new AESPasswordEncoder();
        this.velocityEngine = velocityEngine;
    }

    @RequestMapping(value = {"/forgot-password"}, method = RequestMethod.GET)
    public String forgotPassword() {
        return "forgotPassword";
    }

    @RequestMapping(value = {"/email-sent-successfully"}, method = RequestMethod.GET)
    public String emailStatus() {
        return "emailStatus";
    }

    @RequestMapping(value = {"/email-not-exist"}, method = RequestMethod.GET)
    public String emailNotExistError() {
        return "emailNotExistError";
    }

    public String getContentFromTemplate(Map<String, Object> model, String template) throws ResourceNotFoundException {
        StringBuffer content = new StringBuffer();

        try {
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/bodyEmail.vm","UTF-8", model));
            velocityEngine.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String getBodyToSend(String username , String password){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> model = new HashMap();
        model.put("username", username);
        model.put("password", password);

        String body = getContentFromTemplate(model, "templates/bodyEmail.vm");
        stringBuilder.append(body);

        return stringBuilder.toString();
    }

    @RequestMapping(value = {"/sendEmail"}, method = RequestMethod.POST)
    public String sendEmail(@RequestParam String email) {

        String username;
        String password;
        Usuario usuario = usuarioService.getByEmail(email);

        if (usuario == null){
            return "emailNotExistError";
        }

        username = usuario.getUsername();
        password = usuario.getPassword();

        Email mailConstructor = new Email();
        mailConstructor.setTo(new String[]{email});
        mailConstructor.setSubject("Solicitud de credenciales SCOPESI");
        try {
            if(password != null) {
                password = encoder.decrypt(password);
                mailConstructor.setText(getBodyToSend( username , password));
                emailService.send(mailConstructor);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }

        return "emailStatus";
    }
}
