package meru.application.designer.repository;

import java.util.ArrayList;
import java.util.List;

import meru.application.designer.Application;
import meru.repository.metadata.MetadataFile;
import meru.repository.metadata.MetadataRepository;
import meru.repository.metadata.RootFolder;

public class ApplicationRepository {

    private MetadataRepository mMetadataRepository;

    public ApplicationRepository(MetadataRepository metadataRepository) {

        mMetadataRepository = metadataRepository;
    }

    public List<Application> getApplications() {

        List<MetadataFile> folders = mMetadataRepository.list(MetadataFile.PATH_SEPARATOR);

        List<Application> applications = null;

        if (folders != null) {
            applications = new ArrayList<Application>(folders.size());

            for (MetadataFile folder : folders) {
                Application application = new Application(folder.getGlobalName(),
                                                          folder.getName());
                applications.add(application);
            }
        }

        return applications;
    }

    public Application getApplication(String name) {

        String parentFolder = MetadataFile.PATH_SEPARATOR;
        
        if (name.indexOf(MetadataFile.PATH_SEPARATOR_CHAR) > 0) {
            
            parentFolder = MetadataFile.PATH_SEPARATOR+name.substring(0, name.lastIndexOf(MetadataFile.PATH_SEPARATOR_CHAR));
            name = name.substring(name.lastIndexOf(MetadataFile.PATH_SEPARATOR_CHAR)+1);
        }
        
        
        List<MetadataFile> folders = mMetadataRepository.list(parentFolder);

        if (folders != null) {

            for (MetadataFile folder : folders) {
                if (folder.getName()
                          .equals(name)) {

                    return new Application(folder.getGlobalName(),
                                           folder.getName());
                }
            }
        }

        throw new RuntimeException("Application '" + name + "' is not found");
    }

    public RootFolder getRootFolder() {
        return mMetadataRepository.getRootFolder();
    }

    public void createApplication(String name) {

        if (name.contains(MetadataFile.PATH_SEPARATOR)) {
            throw new IllegalArgumentException("Application can not contain "
                    + MetadataFile.PATH_SEPARATOR + " character");
        }

        mMetadataRepository.createFolder(name);
    }

    public void modifyApplication(String oldName, String newName) {

    }

    public void deleteApplication(String name) {

    }
}
