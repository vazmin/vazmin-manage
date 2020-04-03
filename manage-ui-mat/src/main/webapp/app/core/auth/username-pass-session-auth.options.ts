// TODO postfix to options
export interface AuthModuleConfig {
    alwaysFail?: boolean;
    rememberMe?: boolean;
    endpoint?: string;
    method?: string;
    redirect?: {
        success?: string | null;
        failure?: string | null;
    };
    defaultErrors?: string[];
    defaultMessages?: string[];
}


// TODO postfix to options
export interface AuthSessionServiceConfig {
    baseEndpoint?: string;
    login?: boolean | AuthModuleConfig;
    logout?: boolean | AuthModuleConfig;
    token?: {
        key?: string;
        getter?: Function;
    };
    errors?: {
        key?: string;
        getter?: Function;
    };
    messages?: {
        key?: string;
        getter?: Function;
    };
    validation?: {
        password?: {
            required?: boolean;
            minLength?: number | null;
            maxLength?: number | null;
            regexp?: string | null;
        };
        email?: {
            required?: boolean;
            regexp?: string | null;
        };
        fullName?: {
            required?: boolean;
            minLength?: number | null;
            maxLength?: number | null;
            regexp?: string | null;
        };
    };
}
