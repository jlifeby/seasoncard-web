package com.jlife.mailsender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MailSenderRunnable implements Runnable {

    private MailSender mailSender;

    private Object[] params;

    public MailSenderRunnable(MailSender mailSender, Object... params) {
        this.mailSender = mailSender;
        this.params = params;
    }

    @Override
    public void run() {
        Class cls = MailSenderImpl.class;
        Class[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }
        try {
            Method method = cls.getDeclaredMethod("send", classes);
            method.invoke(mailSender, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}