package gr.york.mobiledev2026.data.remote;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a String field that should be populated from Last.fm's image format,
 * which is a JSON array of {"#text": url, "size": ...} objects rather than a plain string.
 */
@Retention(RetentionPolicy.RUNTIME)
@JsonQualifier
public @interface LastFmImageUrl {
}
