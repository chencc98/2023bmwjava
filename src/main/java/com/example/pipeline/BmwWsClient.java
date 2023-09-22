package com.example.pipeline;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.ApiClient;
import com.databricks.sdk.core.ConfigLoader;
import com.databricks.sdk.core.DatabricksConfig;
import com.databricks.sdk.mixin.ClustersExt;
import com.databricks.sdk.mixin.DbfsExt;
import com.databricks.sdk.mixin.SecretsExt;
import com.databricks.sdk.service.catalog.ArtifactAllowlistsAPI;
import com.databricks.sdk.service.catalog.ArtifactAllowlistsService;
import com.databricks.sdk.service.catalog.CatalogsAPI;
import com.databricks.sdk.service.catalog.CatalogsService;
import com.databricks.sdk.service.catalog.ConnectionsAPI;
import com.databricks.sdk.service.catalog.ConnectionsService;
import com.databricks.sdk.service.catalog.ExternalLocationsAPI;
import com.databricks.sdk.service.catalog.ExternalLocationsService;
import com.databricks.sdk.service.catalog.FunctionsAPI;
import com.databricks.sdk.service.catalog.FunctionsService;
import com.databricks.sdk.service.catalog.GrantsAPI;
import com.databricks.sdk.service.catalog.GrantsService;
import com.databricks.sdk.service.catalog.MetastoresAPI;
import com.databricks.sdk.service.catalog.MetastoresService;
import com.databricks.sdk.service.catalog.ModelVersionsAPI;
import com.databricks.sdk.service.catalog.ModelVersionsService;
import com.databricks.sdk.service.catalog.RegisteredModelsAPI;
import com.databricks.sdk.service.catalog.RegisteredModelsService;
import com.databricks.sdk.service.catalog.SchemasAPI;
import com.databricks.sdk.service.catalog.SchemasService;
import com.databricks.sdk.service.catalog.StorageCredentialsAPI;
import com.databricks.sdk.service.catalog.StorageCredentialsService;
import com.databricks.sdk.service.catalog.SystemSchemasAPI;
import com.databricks.sdk.service.catalog.SystemSchemasService;
import com.databricks.sdk.service.catalog.TableConstraintsAPI;
import com.databricks.sdk.service.catalog.TableConstraintsService;
import com.databricks.sdk.service.catalog.TablesAPI;
import com.databricks.sdk.service.catalog.TablesService;
import com.databricks.sdk.service.catalog.VolumesAPI;
import com.databricks.sdk.service.catalog.VolumesService;
import com.databricks.sdk.service.catalog.WorkspaceBindingsAPI;
import com.databricks.sdk.service.catalog.WorkspaceBindingsService;
import com.databricks.sdk.service.compute.ClusterPoliciesAPI;
import com.databricks.sdk.service.compute.ClusterPoliciesService;
import com.databricks.sdk.service.compute.ClustersService;
import com.databricks.sdk.service.compute.CommandExecutionAPI;
import com.databricks.sdk.service.compute.CommandExecutionService;
import com.databricks.sdk.service.compute.GlobalInitScriptsAPI;
import com.databricks.sdk.service.compute.GlobalInitScriptsService;
import com.databricks.sdk.service.compute.InstancePoolsAPI;
import com.databricks.sdk.service.compute.InstancePoolsService;
import com.databricks.sdk.service.compute.InstanceProfilesAPI;
import com.databricks.sdk.service.compute.InstanceProfilesService;
import com.databricks.sdk.service.compute.LibrariesAPI;
import com.databricks.sdk.service.compute.LibrariesService;
import com.databricks.sdk.service.compute.PolicyFamiliesAPI;
import com.databricks.sdk.service.compute.PolicyFamiliesService;
import com.databricks.sdk.service.files.DbfsService;
import com.databricks.sdk.service.files.FilesAPI;
import com.databricks.sdk.service.files.FilesService;
import com.databricks.sdk.service.iam.AccountAccessControlProxyAPI;
import com.databricks.sdk.service.iam.AccountAccessControlProxyService;
import com.databricks.sdk.service.iam.CurrentUserAPI;
import com.databricks.sdk.service.iam.CurrentUserService;
import com.databricks.sdk.service.iam.GroupsAPI;
import com.databricks.sdk.service.iam.GroupsService;
import com.databricks.sdk.service.iam.PermissionsAPI;
import com.databricks.sdk.service.iam.PermissionsService;
import com.databricks.sdk.service.iam.ServicePrincipalsAPI;
import com.databricks.sdk.service.iam.ServicePrincipalsService;
import com.databricks.sdk.service.iam.UsersAPI;
import com.databricks.sdk.service.iam.UsersService;
import com.databricks.sdk.service.jobs.JobsAPI;
import com.databricks.sdk.service.jobs.JobsService;
import com.databricks.sdk.service.ml.ExperimentsAPI;
import com.databricks.sdk.service.ml.ExperimentsService;
import com.databricks.sdk.service.ml.ModelRegistryAPI;
import com.databricks.sdk.service.ml.ModelRegistryService;
import com.databricks.sdk.service.pipelines.PipelinesAPI;
import com.databricks.sdk.service.pipelines.PipelinesService;
import com.databricks.sdk.service.serving.ServingEndpointsAPI;
import com.databricks.sdk.service.serving.ServingEndpointsService;
import com.databricks.sdk.service.settings.IpAccessListsAPI;
import com.databricks.sdk.service.settings.IpAccessListsService;
import com.databricks.sdk.service.settings.TokenManagementAPI;
import com.databricks.sdk.service.settings.TokenManagementService;
import com.databricks.sdk.service.settings.TokensAPI;
import com.databricks.sdk.service.settings.TokensService;
import com.databricks.sdk.service.settings.WorkspaceConfAPI;
import com.databricks.sdk.service.settings.WorkspaceConfService;
import com.databricks.sdk.service.sharing.CleanRoomsAPI;
import com.databricks.sdk.service.sharing.CleanRoomsService;
import com.databricks.sdk.service.sharing.ProvidersAPI;
import com.databricks.sdk.service.sharing.ProvidersService;
import com.databricks.sdk.service.sharing.RecipientActivationAPI;
import com.databricks.sdk.service.sharing.RecipientActivationService;
import com.databricks.sdk.service.sharing.RecipientsAPI;
import com.databricks.sdk.service.sharing.RecipientsService;
import com.databricks.sdk.service.sharing.SharesAPI;
import com.databricks.sdk.service.sharing.SharesService;
import com.databricks.sdk.service.sql.AlertsAPI;
import com.databricks.sdk.service.sql.AlertsService;
import com.databricks.sdk.service.sql.DashboardWidgetsAPI;
import com.databricks.sdk.service.sql.DashboardWidgetsService;
import com.databricks.sdk.service.sql.DashboardsAPI;
import com.databricks.sdk.service.sql.DashboardsService;
import com.databricks.sdk.service.sql.DataSourcesAPI;
import com.databricks.sdk.service.sql.DataSourcesService;
import com.databricks.sdk.service.sql.DbsqlPermissionsAPI;
import com.databricks.sdk.service.sql.DbsqlPermissionsService;
import com.databricks.sdk.service.sql.QueriesAPI;
import com.databricks.sdk.service.sql.QueriesService;
import com.databricks.sdk.service.sql.QueryHistoryAPI;
import com.databricks.sdk.service.sql.QueryHistoryService;
import com.databricks.sdk.service.sql.QueryVisualizationsAPI;
import com.databricks.sdk.service.sql.QueryVisualizationsService;
import com.databricks.sdk.service.sql.StatementExecutionAPI;
import com.databricks.sdk.service.sql.StatementExecutionService;
import com.databricks.sdk.service.sql.WarehousesAPI;
import com.databricks.sdk.service.sql.WarehousesService;
import com.databricks.sdk.service.workspace.GitCredentialsAPI;
import com.databricks.sdk.service.workspace.GitCredentialsService;
import com.databricks.sdk.service.workspace.ReposAPI;
import com.databricks.sdk.service.workspace.ReposService;
import com.databricks.sdk.service.workspace.SecretsService;
import com.databricks.sdk.service.workspace.WorkspaceAPI;
import com.databricks.sdk.service.workspace.WorkspaceService;

public class BmwWsClient {
   private final ApiClient apiClient;
   private final DatabricksConfig config;
   private AccountAccessControlProxyAPI accountAccessControlProxyAPI;
   private AlertsAPI alertsAPI;
   private ArtifactAllowlistsAPI artifactAllowlistsAPI;
   private CatalogsAPI catalogsAPI;
   private CleanRoomsAPI cleanRoomsAPI;
   private ClusterPoliciesAPI clusterPoliciesAPI;
   private ClustersExt clustersAPI;
   private CommandExecutionAPI commandExecutionAPI;
   private ConnectionsAPI connectionsAPI;
   private CurrentUserAPI currentUserAPI;
   private DashboardWidgetsAPI dashboardWidgetsAPI;
   private DashboardsAPI dashboardsAPI;
   private DataSourcesAPI dataSourcesAPI;
   private DbfsExt dbfsAPI;
   private DbsqlPermissionsAPI dbsqlPermissionsAPI;
   private ExperimentsAPI experimentsAPI;
   private ExternalLocationsAPI externalLocationsAPI;
   private FilesAPI filesAPI;
   private FunctionsAPI functionsAPI;
   private GitCredentialsAPI gitCredentialsAPI;
   private GlobalInitScriptsAPI globalInitScriptsAPI;
   private GrantsAPI grantsAPI;
   private GroupsAPI groupsAPI;
   private InstancePoolsAPI instancePoolsAPI;
   private InstanceProfilesAPI instanceProfilesAPI;
   private IpAccessListsAPI ipAccessListsAPI;
   private JobsAPI jobsAPI;
   private LibrariesAPI librariesAPI;
   private MetastoresAPI metastoresAPI;
   private ModelRegistryAPI modelRegistryAPI;
   private ModelVersionsAPI modelVersionsAPI;
   private PermissionsAPI permissionsAPI;
   private PipelinesAPI pipelinesAPI;
   private PolicyFamiliesAPI policyFamiliesAPI;
   private ProvidersAPI providersAPI;
   private QueriesAPI queriesAPI;
   private QueryHistoryAPI queryHistoryAPI;
   private QueryVisualizationsAPI queryVisualizationsAPI;
   private RecipientActivationAPI recipientActivationAPI;
   private RecipientsAPI recipientsAPI;
   private RegisteredModelsAPI registeredModelsAPI;
   private ReposAPI reposAPI;
   private SchemasAPI schemasAPI;
   private SecretsExt secretsAPI;
   private ServicePrincipalsAPI servicePrincipalsAPI;
   private ServingEndpointsAPI servingEndpointsAPI;
   private SharesAPI sharesAPI;
   private StatementExecutionAPI statementExecutionAPI;
   private StorageCredentialsAPI storageCredentialsAPI;
   private SystemSchemasAPI systemSchemasAPI;
   private TableConstraintsAPI tableConstraintsAPI;
   private TablesAPI tablesAPI;
   private TokenManagementAPI tokenManagementAPI;
   private TokensAPI tokensAPI;
   private UsersAPI usersAPI;
   private VolumesAPI volumesAPI;
   private WarehousesAPI warehousesAPI;
   private WorkspaceAPI workspaceAPI;
   private WorkspaceBindingsAPI workspaceBindingsAPI;
   private WorkspaceConfAPI workspaceConfAPI;

   public BmwWsClient() {
      this(ConfigLoader.getDefault());
   }

   public BmwWsClient(DatabricksConfig config) {
      this.config = config;
      this.apiClient = new BmwApiClient(config);
      this.accountAccessControlProxyAPI = new AccountAccessControlProxyAPI(this.apiClient);
      this.alertsAPI = new AlertsAPI(this.apiClient);
      this.artifactAllowlistsAPI = new ArtifactAllowlistsAPI(this.apiClient);
      this.catalogsAPI = new CatalogsAPI(this.apiClient);
      this.cleanRoomsAPI = new CleanRoomsAPI(this.apiClient);
      this.clusterPoliciesAPI = new ClusterPoliciesAPI(this.apiClient);
      this.clustersAPI = new ClustersExt(this.apiClient);
      this.commandExecutionAPI = new CommandExecutionAPI(this.apiClient);
      this.connectionsAPI = new ConnectionsAPI(this.apiClient);
      this.currentUserAPI = new CurrentUserAPI(this.apiClient);
      this.dashboardWidgetsAPI = new DashboardWidgetsAPI(this.apiClient);
      this.dashboardsAPI = new DashboardsAPI(this.apiClient);
      this.dataSourcesAPI = new DataSourcesAPI(this.apiClient);
      this.dbfsAPI = new DbfsExt(this.apiClient);
      this.dbsqlPermissionsAPI = new DbsqlPermissionsAPI(this.apiClient);
      this.experimentsAPI = new ExperimentsAPI(this.apiClient);
      this.externalLocationsAPI = new ExternalLocationsAPI(this.apiClient);
      this.filesAPI = new FilesAPI(this.apiClient);
      this.functionsAPI = new FunctionsAPI(this.apiClient);
      this.gitCredentialsAPI = new GitCredentialsAPI(this.apiClient);
      this.globalInitScriptsAPI = new GlobalInitScriptsAPI(this.apiClient);
      this.grantsAPI = new GrantsAPI(this.apiClient);
      this.groupsAPI = new GroupsAPI(this.apiClient);
      this.instancePoolsAPI = new InstancePoolsAPI(this.apiClient);
      this.instanceProfilesAPI = new InstanceProfilesAPI(this.apiClient);
      this.ipAccessListsAPI = new IpAccessListsAPI(this.apiClient);
      this.jobsAPI = new JobsAPI(this.apiClient);
      this.librariesAPI = new LibrariesAPI(this.apiClient);
      this.metastoresAPI = new MetastoresAPI(this.apiClient);
      this.modelRegistryAPI = new ModelRegistryAPI(this.apiClient);
      this.modelVersionsAPI = new ModelVersionsAPI(this.apiClient);
      this.permissionsAPI = new PermissionsAPI(this.apiClient);
      this.pipelinesAPI = new PipelinesAPI(this.apiClient);
      this.policyFamiliesAPI = new PolicyFamiliesAPI(this.apiClient);
      this.providersAPI = new ProvidersAPI(this.apiClient);
      this.queriesAPI = new QueriesAPI(this.apiClient);
      this.queryHistoryAPI = new QueryHistoryAPI(this.apiClient);
      this.queryVisualizationsAPI = new QueryVisualizationsAPI(this.apiClient);
      this.recipientActivationAPI = new RecipientActivationAPI(this.apiClient);
      this.recipientsAPI = new RecipientsAPI(this.apiClient);
      this.registeredModelsAPI = new RegisteredModelsAPI(this.apiClient);
      this.reposAPI = new ReposAPI(this.apiClient);
      this.schemasAPI = new SchemasAPI(this.apiClient);
      this.secretsAPI = new SecretsExt(this.apiClient);
      this.servicePrincipalsAPI = new ServicePrincipalsAPI(this.apiClient);
      this.servingEndpointsAPI = new ServingEndpointsAPI(this.apiClient);
      this.sharesAPI = new SharesAPI(this.apiClient);
      this.statementExecutionAPI = new StatementExecutionAPI(this.apiClient);
      this.storageCredentialsAPI = new StorageCredentialsAPI(this.apiClient);
      this.systemSchemasAPI = new SystemSchemasAPI(this.apiClient);
      this.tableConstraintsAPI = new TableConstraintsAPI(this.apiClient);
      this.tablesAPI = new TablesAPI(this.apiClient);
      this.tokenManagementAPI = new TokenManagementAPI(this.apiClient);
      this.tokensAPI = new TokensAPI(this.apiClient);
      this.usersAPI = new UsersAPI(this.apiClient);
      this.volumesAPI = new VolumesAPI(this.apiClient);
      this.warehousesAPI = new WarehousesAPI(this.apiClient);
      this.workspaceAPI = new WorkspaceAPI(this.apiClient);
      this.workspaceBindingsAPI = new WorkspaceBindingsAPI(this.apiClient);
      this.workspaceConfAPI = new WorkspaceConfAPI(this.apiClient);
   }

   public BmwWsClient(boolean mock) {
      this(mock, (ApiClient)null);
   }

   public BmwWsClient(boolean mock, ApiClient apiClient) {
      this.apiClient = apiClient;
      this.config = null;
   }

   public AccountAccessControlProxyAPI accountAccessControlProxy() {
      return this.accountAccessControlProxyAPI;
   }

   public AlertsAPI alerts() {
      return this.alertsAPI;
   }

   public ArtifactAllowlistsAPI artifactAllowlists() {
      return this.artifactAllowlistsAPI;
   }

   public CatalogsAPI catalogs() {
      return this.catalogsAPI;
   }

   public CleanRoomsAPI cleanRooms() {
      return this.cleanRoomsAPI;
   }

   public ClusterPoliciesAPI clusterPolicies() {
      return this.clusterPoliciesAPI;
   }

   public ClustersExt clusters() {
      return this.clustersAPI;
   }

   public CommandExecutionAPI commandExecution() {
      return this.commandExecutionAPI;
   }

   public ConnectionsAPI connections() {
      return this.connectionsAPI;
   }

   public CurrentUserAPI currentUser() {
      return this.currentUserAPI;
   }

   public DashboardWidgetsAPI dashboardWidgets() {
      return this.dashboardWidgetsAPI;
   }

   public DashboardsAPI dashboards() {
      return this.dashboardsAPI;
   }

   public DataSourcesAPI dataSources() {
      return this.dataSourcesAPI;
   }

   public DbfsExt dbfs() {
      return this.dbfsAPI;
   }

   public DbsqlPermissionsAPI dbsqlPermissions() {
      return this.dbsqlPermissionsAPI;
   }

   public ExperimentsAPI experiments() {
      return this.experimentsAPI;
   }

   public ExternalLocationsAPI externalLocations() {
      return this.externalLocationsAPI;
   }

   public FilesAPI files() {
      return this.filesAPI;
   }

   public FunctionsAPI functions() {
      return this.functionsAPI;
   }

   public GitCredentialsAPI gitCredentials() {
      return this.gitCredentialsAPI;
   }

   public GlobalInitScriptsAPI globalInitScripts() {
      return this.globalInitScriptsAPI;
   }

   public GrantsAPI grants() {
      return this.grantsAPI;
   }

   public GroupsAPI groups() {
      return this.groupsAPI;
   }

   public InstancePoolsAPI instancePools() {
      return this.instancePoolsAPI;
   }

   public InstanceProfilesAPI instanceProfiles() {
      return this.instanceProfilesAPI;
   }

   public IpAccessListsAPI ipAccessLists() {
      return this.ipAccessListsAPI;
   }

   public JobsAPI jobs() {
      return this.jobsAPI;
   }

   public LibrariesAPI libraries() {
      return this.librariesAPI;
   }

   public MetastoresAPI metastores() {
      return this.metastoresAPI;
   }

   public ModelRegistryAPI modelRegistry() {
      return this.modelRegistryAPI;
   }

   public ModelVersionsAPI modelVersions() {
      return this.modelVersionsAPI;
   }

   public PermissionsAPI permissions() {
      return this.permissionsAPI;
   }

   public PipelinesAPI pipelines() {
      return this.pipelinesAPI;
   }

   public PolicyFamiliesAPI policyFamilies() {
      return this.policyFamiliesAPI;
   }

   public ProvidersAPI providers() {
      return this.providersAPI;
   }

   public QueriesAPI queries() {
      return this.queriesAPI;
   }

   public QueryHistoryAPI queryHistory() {
      return this.queryHistoryAPI;
   }

   public QueryVisualizationsAPI queryVisualizations() {
      return this.queryVisualizationsAPI;
   }

   public RecipientActivationAPI recipientActivation() {
      return this.recipientActivationAPI;
   }

   public RecipientsAPI recipients() {
      return this.recipientsAPI;
   }

   public RegisteredModelsAPI registeredModels() {
      return this.registeredModelsAPI;
   }

   public ReposAPI repos() {
      return this.reposAPI;
   }

   public SchemasAPI schemas() {
      return this.schemasAPI;
   }

   public SecretsExt secrets() {
      return this.secretsAPI;
   }

   public ServicePrincipalsAPI servicePrincipals() {
      return this.servicePrincipalsAPI;
   }

   public ServingEndpointsAPI servingEndpoints() {
      return this.servingEndpointsAPI;
   }

   public SharesAPI shares() {
      return this.sharesAPI;
   }

   public StatementExecutionAPI statementExecution() {
      return this.statementExecutionAPI;
   }

   public StorageCredentialsAPI storageCredentials() {
      return this.storageCredentialsAPI;
   }

   public SystemSchemasAPI systemSchemas() {
      return this.systemSchemasAPI;
   }

   public TableConstraintsAPI tableConstraints() {
      return this.tableConstraintsAPI;
   }

   public TablesAPI tables() {
      return this.tablesAPI;
   }

   public TokenManagementAPI tokenManagement() {
      return this.tokenManagementAPI;
   }

   public TokensAPI tokens() {
      return this.tokensAPI;
   }

   public UsersAPI users() {
      return this.usersAPI;
   }

   public VolumesAPI volumes() {
      return this.volumesAPI;
   }

   public WarehousesAPI warehouses() {
      return this.warehousesAPI;
   }

   public WorkspaceAPI workspace() {
      return this.workspaceAPI;
   }

   public WorkspaceBindingsAPI workspaceBindings() {
      return this.workspaceBindingsAPI;
   }

   public WorkspaceConfAPI workspaceConf() {
      return this.workspaceConfAPI;
   }

   public BmwWsClient withAccountAccessControlProxyImpl(AccountAccessControlProxyService accountAccessControlProxy) {
      this.accountAccessControlProxyAPI = new AccountAccessControlProxyAPI(accountAccessControlProxy);
      return this;
   }

   public BmwWsClient withAlertsImpl(AlertsService alerts) {
      this.alertsAPI = new AlertsAPI(alerts);
      return this;
   }

   public BmwWsClient withArtifactAllowlistsImpl(ArtifactAllowlistsService artifactAllowlists) {
      this.artifactAllowlistsAPI = new ArtifactAllowlistsAPI(artifactAllowlists);
      return this;
   }

   public BmwWsClient withCatalogsImpl(CatalogsService catalogs) {
      this.catalogsAPI = new CatalogsAPI(catalogs);
      return this;
   }

   public BmwWsClient withCleanRoomsImpl(CleanRoomsService cleanRooms) {
      this.cleanRoomsAPI = new CleanRoomsAPI(cleanRooms);
      return this;
   }

   public BmwWsClient withClusterPoliciesImpl(ClusterPoliciesService clusterPolicies) {
      this.clusterPoliciesAPI = new ClusterPoliciesAPI(clusterPolicies);
      return this;
   }

   public BmwWsClient withClustersImpl(ClustersService clusters) {
      this.clustersAPI = new ClustersExt(clusters);
      return this;
   }

   public BmwWsClient withCommandExecutionImpl(CommandExecutionService commandExecution) {
      this.commandExecutionAPI = new CommandExecutionAPI(commandExecution);
      return this;
   }

   public BmwWsClient withConnectionsImpl(ConnectionsService connections) {
      this.connectionsAPI = new ConnectionsAPI(connections);
      return this;
   }

   public BmwWsClient withCurrentUserImpl(CurrentUserService currentUser) {
      this.currentUserAPI = new CurrentUserAPI(currentUser);
      return this;
   }

   public BmwWsClient withDashboardWidgetsImpl(DashboardWidgetsService dashboardWidgets) {
      this.dashboardWidgetsAPI = new DashboardWidgetsAPI(dashboardWidgets);
      return this;
   }

   public BmwWsClient withDashboardsImpl(DashboardsService dashboards) {
      this.dashboardsAPI = new DashboardsAPI(dashboards);
      return this;
   }

   public BmwWsClient withDataSourcesImpl(DataSourcesService dataSources) {
      this.dataSourcesAPI = new DataSourcesAPI(dataSources);
      return this;
   }

   public BmwWsClient withDbfsImpl(DbfsService dbfs) {
      this.dbfsAPI = new DbfsExt(dbfs);
      return this;
   }

   public BmwWsClient withDbsqlPermissionsImpl(DbsqlPermissionsService dbsqlPermissions) {
      this.dbsqlPermissionsAPI = new DbsqlPermissionsAPI(dbsqlPermissions);
      return this;
   }

   public BmwWsClient withExperimentsImpl(ExperimentsService experiments) {
      this.experimentsAPI = new ExperimentsAPI(experiments);
      return this;
   }

   public BmwWsClient withExternalLocationsImpl(ExternalLocationsService externalLocations) {
      this.externalLocationsAPI = new ExternalLocationsAPI(externalLocations);
      return this;
   }

   public BmwWsClient withFilesImpl(FilesService files) {
      this.filesAPI = new FilesAPI(files);
      return this;
   }

   public BmwWsClient withFunctionsImpl(FunctionsService functions) {
      this.functionsAPI = new FunctionsAPI(functions);
      return this;
   }

   public BmwWsClient withGitCredentialsImpl(GitCredentialsService gitCredentials) {
      this.gitCredentialsAPI = new GitCredentialsAPI(gitCredentials);
      return this;
   }

   public BmwWsClient withGlobalInitScriptsImpl(GlobalInitScriptsService globalInitScripts) {
      this.globalInitScriptsAPI = new GlobalInitScriptsAPI(globalInitScripts);
      return this;
   }

   public BmwWsClient withGrantsImpl(GrantsService grants) {
      this.grantsAPI = new GrantsAPI(grants);
      return this;
   }

   public BmwWsClient withGroupsImpl(GroupsService groups) {
      this.groupsAPI = new GroupsAPI(groups);
      return this;
   }

   public BmwWsClient withInstancePoolsImpl(InstancePoolsService instancePools) {
      this.instancePoolsAPI = new InstancePoolsAPI(instancePools);
      return this;
   }

   public BmwWsClient withInstanceProfilesImpl(InstanceProfilesService instanceProfiles) {
      this.instanceProfilesAPI = new InstanceProfilesAPI(instanceProfiles);
      return this;
   }

   public BmwWsClient withIpAccessListsImpl(IpAccessListsService ipAccessLists) {
      this.ipAccessListsAPI = new IpAccessListsAPI(ipAccessLists);
      return this;
   }

   public BmwWsClient withJobsImpl(JobsService jobs) {
      this.jobsAPI = new JobsAPI(jobs);
      return this;
   }

   public BmwWsClient withLibrariesImpl(LibrariesService libraries) {
      this.librariesAPI = new LibrariesAPI(libraries);
      return this;
   }

   public BmwWsClient withMetastoresImpl(MetastoresService metastores) {
      this.metastoresAPI = new MetastoresAPI(metastores);
      return this;
   }

   public BmwWsClient withModelRegistryImpl(ModelRegistryService modelRegistry) {
      this.modelRegistryAPI = new ModelRegistryAPI(modelRegistry);
      return this;
   }

   public BmwWsClient withModelVersionsImpl(ModelVersionsService modelVersions) {
      this.modelVersionsAPI = new ModelVersionsAPI(modelVersions);
      return this;
   }

   public BmwWsClient withPermissionsImpl(PermissionsService permissions) {
      this.permissionsAPI = new PermissionsAPI(permissions);
      return this;
   }

   public BmwWsClient withPipelinesImpl(PipelinesService pipelines) {
      this.pipelinesAPI = new PipelinesAPI(pipelines);
      return this;
   }

   public BmwWsClient withPolicyFamiliesImpl(PolicyFamiliesService policyFamilies) {
      this.policyFamiliesAPI = new PolicyFamiliesAPI(policyFamilies);
      return this;
   }

   public BmwWsClient withProvidersImpl(ProvidersService providers) {
      this.providersAPI = new ProvidersAPI(providers);
      return this;
   }

   public BmwWsClient withQueriesImpl(QueriesService queries) {
      this.queriesAPI = new QueriesAPI(queries);
      return this;
   }

   public BmwWsClient withQueryHistoryImpl(QueryHistoryService queryHistory) {
      this.queryHistoryAPI = new QueryHistoryAPI(queryHistory);
      return this;
   }

   public BmwWsClient withQueryVisualizationsImpl(QueryVisualizationsService queryVisualizations) {
      this.queryVisualizationsAPI = new QueryVisualizationsAPI(queryVisualizations);
      return this;
   }

   public BmwWsClient withRecipientActivationImpl(RecipientActivationService recipientActivation) {
      this.recipientActivationAPI = new RecipientActivationAPI(recipientActivation);
      return this;
   }

   public BmwWsClient withRecipientsImpl(RecipientsService recipients) {
      this.recipientsAPI = new RecipientsAPI(recipients);
      return this;
   }

   public BmwWsClient withRegisteredModelsImpl(RegisteredModelsService registeredModels) {
      this.registeredModelsAPI = new RegisteredModelsAPI(registeredModels);
      return this;
   }

   public BmwWsClient withReposImpl(ReposService repos) {
      this.reposAPI = new ReposAPI(repos);
      return this;
   }

   public BmwWsClient withSchemasImpl(SchemasService schemas) {
      this.schemasAPI = new SchemasAPI(schemas);
      return this;
   }

   public BmwWsClient withSecretsImpl(SecretsService secrets) {
      this.secretsAPI = new SecretsExt(secrets);
      return this;
   }

   public BmwWsClient withServicePrincipalsImpl(ServicePrincipalsService servicePrincipals) {
      this.servicePrincipalsAPI = new ServicePrincipalsAPI(servicePrincipals);
      return this;
   }

   public BmwWsClient withServingEndpointsImpl(ServingEndpointsService servingEndpoints) {
      this.servingEndpointsAPI = new ServingEndpointsAPI(servingEndpoints);
      return this;
   }

   public BmwWsClient withSharesImpl(SharesService shares) {
      this.sharesAPI = new SharesAPI(shares);
      return this;
   }

   public BmwWsClient withStatementExecutionImpl(StatementExecutionService statementExecution) {
      this.statementExecutionAPI = new StatementExecutionAPI(statementExecution);
      return this;
   }

   public BmwWsClient withStorageCredentialsImpl(StorageCredentialsService storageCredentials) {
      this.storageCredentialsAPI = new StorageCredentialsAPI(storageCredentials);
      return this;
   }

   public BmwWsClient withSystemSchemasImpl(SystemSchemasService systemSchemas) {
      this.systemSchemasAPI = new SystemSchemasAPI(systemSchemas);
      return this;
   }

   public BmwWsClient withTableConstraintsImpl(TableConstraintsService tableConstraints) {
      this.tableConstraintsAPI = new TableConstraintsAPI(tableConstraints);
      return this;
   }

   public BmwWsClient withTablesImpl(TablesService tables) {
      this.tablesAPI = new TablesAPI(tables);
      return this;
   }

   public BmwWsClient withTokenManagementImpl(TokenManagementService tokenManagement) {
      this.tokenManagementAPI = new TokenManagementAPI(tokenManagement);
      return this;
   }

   public BmwWsClient withTokensImpl(TokensService tokens) {
      this.tokensAPI = new TokensAPI(tokens);
      return this;
   }

   public BmwWsClient withUsersImpl(UsersService users) {
      this.usersAPI = new UsersAPI(users);
      return this;
   }

   public BmwWsClient withVolumesImpl(VolumesService volumes) {
      this.volumesAPI = new VolumesAPI(volumes);
      return this;
   }

   public BmwWsClient withWarehousesImpl(WarehousesService warehouses) {
      this.warehousesAPI = new WarehousesAPI(warehouses);
      return this;
   }

   public BmwWsClient withWorkspaceImpl(WorkspaceService workspace) {
      this.workspaceAPI = new WorkspaceAPI(workspace);
      return this;
   }

   public BmwWsClient withWorkspaceBindingsImpl(WorkspaceBindingsService workspaceBindings) {
      this.workspaceBindingsAPI = new WorkspaceBindingsAPI(workspaceBindings);
      return this;
   }

   public BmwWsClient withWorkspaceConfImpl(WorkspaceConfService workspaceConf) {
      this.workspaceConfAPI = new WorkspaceConfAPI(workspaceConf);
      return this;
   }

   public ApiClient apiClient() {
      return this.apiClient;
   }

   public DatabricksConfig config() {
      return this.config;
   }
}
