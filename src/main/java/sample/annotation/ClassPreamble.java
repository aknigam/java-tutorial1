package sample.annotation;

/**
 * Created by a.nigam on 18/04/16.
 */



public @interface ClassPreamble {
    String author();

    String date();

    int currentRevision() default 1;

    String lastModified() default "N/A";

    String lastModifiedBy() default "N/A";
}