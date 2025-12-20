package com.company.eduboard.global.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HtmlContentProcessor {

    private static final Safelist SAFE = Safelist.relaxed()
            .addTags("table", "thead", "tbody", "tr", "th", "td")
            .addAttributes("table", "class")
            .addAttributes("th", "colspan", "rowspan", "style")
            .addAttributes("td", "colspan", "rowspan", "style")

            .addAttributes("p", "style")
            .addAttributes("h1", "style")
            .addAttributes("h2", "style")
            .addAttributes("h3", "style")
            .addAttributes("h4", "style")
            .addAttributes("h5", "style")

            .addTags("span")
            .addAttributes("span", "style", "class")

            .addAttributes("a", "href", "target", "rel")
            .addProtocols("a", "href", "http", "https");


    public static ProcessedContent process(String rawHtml) {
        String sanitized = sanitize(rawHtml);
        String normalized = normalize(sanitized);
        String text = extractText(sanitized);
        String signature = sha256Hex(normalized);
        return new ProcessedContent(sanitized, text, signature);
    }

    private static String sanitize(String rawHtml) {
        if (rawHtml == null) return "";
        return Jsoup.clean(rawHtml, SAFE);
    }

    // diff 노이즈 줄이기 -> 공백/줄바꿈 정도만
    private static String normalize(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        doc.outputSettings().prettyPrint(true);
        return doc.body().html().trim();
    }

    private static String extractText(String html) {
        return Jsoup.parse(html).text().trim();
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    public record ProcessedContent(String content, String contentText, String contentSignature) {}
}
