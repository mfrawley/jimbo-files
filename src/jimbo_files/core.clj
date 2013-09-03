(ns jimbo-files.core)

(require '[aws.sdk.s3 :as s3],
         '[clojure.java.io :as io],
         '[image-resizer.core :refer :all],
         '[image-resizer.format :as format])

(def s3-cred {:access-key (get (System/getenv) "AWS_S3_ACCESS_KEY")
              :secret-key (get (System/getenv) "AWS_S3_SECRET_KEY")})

(def s3-bucket (get (System/getenv) "AWS_S3_BUCKET"))

(defn print-s3-meta [path]
  println (:metadata (s3/get-object s3-cred s3-bucket path)))

(defn copy-s3-object [output, path]
  (io/copy (:content (s3/get-object s3-cred s3-bucket path))
           output))

(defn resize-jimbo-image [path width height]
  (with-open [input (:content (s3/get-object s3-cred s3-bucket path))]
    (resize input width height)))

