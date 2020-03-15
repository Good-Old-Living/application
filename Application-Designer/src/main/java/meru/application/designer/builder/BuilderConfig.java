package meru.application.designer.builder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import meru.app.config.AppConfig;
import meru.sys.IOSystem;

public class BuilderConfig {

	private static final String BO_DIR = "Business Objects";

	public static final String PROP_DATABASE_TABLE_PREFIX = "database-table-prefix";

	private List<File> mDependantModuleHomes;

	private File mAppRoot;
	private File mAppHome;
	private File[] mModuleHomes;
	private File mDeployHome;
	private AppConfig mAppConfig;
	private Map<String, String> mConfigProperties;

	private Stack<String> mStack;

	public BuilderConfig(AppConfig appConfig, File appRoot, String appId) {

		if (appRoot == null || !appRoot.exists()) {
			throw new IllegalArgumentException("Invalid Application Home : "
					+ appRoot);
		}

		mAppRoot = appRoot;
		mAppHome = new File(appRoot, appId);

		File metadataHome = new File(mAppHome, "metadata");

		mModuleHomes = getModules(metadataHome);

		mDeployHome = new File(mAppHome, "_generated");
		mDeployHome.mkdir();

		mAppConfig = appConfig;

		mStack = new Stack<String>();

		mConfigProperties = IOSystem.readProperties(new File(metadataHome,
				"config.ini"));

		mDependantModuleHomes = new ArrayList<File>(3);

		populateDependantModules(appRoot, mAppHome);
	}

	private void populateDependantModules(File appRoot, File appHome) {

		Map<String, String> configProperties = IOSystem
				.readProperties(new File(appHome, "metadata/config.ini"));

		String modules = configProperties.get("modules");

		if (modules != null) {
			String[] moduleNames = modules.split("[,]");

			for (String moduleName : moduleNames) {
				File moduleFile = new File(appRoot, moduleName);

				File childMetadataHome = new File(moduleFile, "metadata");

				File[] depModules = getModules(childMetadataHome);
				if (depModules != null) {
					for (File file : depModules) {
						mDependantModuleHomes.add(file);
					}
				}

				populateDependantModules(appRoot, moduleFile);
			}

		}

	}

	private File[] getModules(File appFile) {

		File moduleDir = new File(appFile, "app-module");

		return moduleDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith("_");
			}
		});
	}

	public Class<?> getEntityClassRegistry() throws ClassNotFoundException {
		return mAppConfig.getEntityClassRegistryConfig()
				.getEntityClassRegistryClass();
	}

	public String getProperty(String propertyName) {
		return mConfigProperties.get(propertyName);
	}

	public File getApplicationRoot() {
        return mAppRoot;
    }
	
	public File getApplicationParentHome() {
		return mAppHome.getParentFile();
	}

	public String getApplicationName() {
		return mAppHome.getName();
	}

	public File[] getModuleHomes() {
		return mModuleHomes;
	}

	public File[] getDependantModuleHomes() {
		return mDependantModuleHomes.toArray(new File[] {});
	}

	public File getBusinessObjectsHome(File moduleHome) {

		return new File(moduleHome, BO_DIR);
	}

	public File getDeployHome() {

		return mDeployHome;
	}

	public void addPackageName(String packageFolder) {

		mStack.push(packageFolder);

	}

	public void removePackageName(String packageFolder) {

		mStack.pop();

	}

	public String getPackageName() {

		StringBuilder strBuilder = new StringBuilder();
		Iterator<String> packs = mStack.iterator();
		while (packs.hasNext()) {

			if (strBuilder.length() != 0) {
				strBuilder.append('.');
			}

			strBuilder.append(packs.next());

		}

		return strBuilder.toString();
	}

}
