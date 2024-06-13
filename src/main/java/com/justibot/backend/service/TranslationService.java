package com.justibot.backend.service;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranslationService {

    private final TextTranslationClient client;

    public TranslationService(@Value("${azure.translation.api-key}") String apiKey,
                              @Value("${azure.translation.region}") String region) {

        AzureKeyCredential credential = new AzureKeyCredential(apiKey);

        this.client = new TextTranslationClientBuilder()
                .credential(credential)
                .region(region)
                .buildClient();
    }

    public String translateText(String text, String si) {
        List<String> targetLanguages = new ArrayList<>();
        targetLanguages.add("si");

        List<InputTextItem> content = new ArrayList<>();
        content.add(new InputTextItem(text));

        List<TranslatedTextItem> translations = client.translate(
                targetLanguages,
                content,
                null,
                "en",
                TextType.PLAIN,
                null,
                ProfanityAction.NO_ACTION,
                ProfanityMarker.ASTERISK,
                false,
                false,
                null,
                null,
                null,
                false
        );

        StringBuilder translatedText = new StringBuilder();
        for (TranslatedTextItem translation : translations) {
            for (Translation textTranslation : translation.getTranslations()) {
                translatedText.append(textTranslation.getText());
            }
        }

        return translatedText.toString();
    }

    public String translateTextEng(String text, String en) {
        List<String> targetLanguages = new ArrayList<>();
        targetLanguages.add("en");

        List<InputTextItem> content = new ArrayList<>();
        content.add(new InputTextItem(text));

        List<TranslatedTextItem> translations = client.translate(
                targetLanguages,
                content,
                null,
                "si",
                TextType.PLAIN,
                null,
                ProfanityAction.NO_ACTION,
                ProfanityMarker.ASTERISK,
                false,
                false,
                null,
                null,
                null,
                false
        );

        StringBuilder translatedText = new StringBuilder();
        for (TranslatedTextItem translation : translations) {
            for (Translation textTranslation : translation.getTranslations()) {
                translatedText.append(textTranslation.getText());
            }
        }

        return translatedText.toString();
    }

//    public String detectLanguage(String text) {
//        client.getLanguages();
//        DetectedLanguage detectedLanguage = client.detectLanguage(text).getDetectedLanguages().get(0);
//        return detectedLanguage.getLanguage();
//    }
}
