# 上传本地仓库
mvn install:install-file -Dfile=fastdfs_client_v1.24.jar -DgroupId=org.csource -DartifactId=fastdfs_client -Dversion=v1.24 -Dpackaging=jar
mvn install:install-file -Dfile=icepdf-core.jar -DgroupId=icepdf-core -DartifactId=icepdf-core -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=icepdf-viewer.jar -DgroupId=icepdf-viewer -DartifactId=icepdf-viewer -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=jodconverter-cli-2.2.2.jar -DgroupId=com.artofsolving.jodconverter -DartifactId=jodconverter-cli -Dversion=2.2.2 -Dpackaging=jar
mvn install:install-file -Dfile=jodconverter-2.2.2.jar -DgroupId=com.artofsolving.jodconverter -DartifactId=jodconverter -Dversion=2.2.2 -Dpackaging=jar
mvn install:install-file -Dfile=jwordconvert.jar -DgroupId=jwordconvert -DartifactId=jwordconvert -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=sun.misc.BASE64Decoder.jar -DgroupId=BASE64Decoder -DartifactId=BASE64Decoder -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=wcs-java-sdk-1.5.3.jar -DgroupId=wcs-java-sdk -DartifactId=wcs-java-sdk -Dversion=1.5.3 -Dpackaging=jar