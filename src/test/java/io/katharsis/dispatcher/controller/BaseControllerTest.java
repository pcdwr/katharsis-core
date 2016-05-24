package io.katharsis.dispatcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.katharsis.jackson.JsonApiModuleBuilder;
import io.katharsis.locator.SampleJsonServiceLocator;
import io.katharsis.queryParams.DefaultQueryParamsParser;
import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.QueryParamsBuilder;
import io.katharsis.repository.RepositoryMethodParameterProvider;
import io.katharsis.repository.mock.NewInstanceRepositoryMethodParameterProvider;
import io.katharsis.resource.field.ResourceFieldNameTransformer;
import io.katharsis.resource.include.IncludeLookupSetter;
import io.katharsis.resource.information.ResourceInformationBuilder;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.resource.registry.ResourceRegistryBuilder;
import io.katharsis.resource.registry.ResourceRegistryBuilderTest;
import io.katharsis.resource.registry.ResourceRegistryTest;
import io.katharsis.utils.parser.TypeParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class BaseControllerTest {
    protected static final QueryParams REQUEST_PARAMS = new QueryParams();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    protected ObjectMapper objectMapper;
    protected ResourceRegistry resourceRegistry;
    protected TypeParser typeParser;
    protected IncludeLookupSetter includeFieldSetter;
    protected RepositoryMethodParameterProvider parameterProvider;
    protected QueryParamsBuilder queryParamsBuilder;

    @Before
    public void prepare() {
        ResourceInformationBuilder resourceInformationBuilder = new ResourceInformationBuilder(
                new ResourceFieldNameTransformer());
        ResourceRegistryBuilder registryBuilder = new ResourceRegistryBuilder(new SampleJsonServiceLocator(),
                resourceInformationBuilder);
        resourceRegistry = registryBuilder
                .build(ResourceRegistryBuilderTest.TEST_MODELS_PACKAGE, ResourceRegistryTest.TEST_MODELS_URL);
        typeParser = new TypeParser();
        includeFieldSetter = new IncludeLookupSetter(resourceRegistry);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonApiModuleBuilder().build(resourceRegistry));
        parameterProvider = new NewInstanceRepositoryMethodParameterProvider();
        queryParamsBuilder = new QueryParamsBuilder(new DefaultQueryParamsParser());
    }
}
