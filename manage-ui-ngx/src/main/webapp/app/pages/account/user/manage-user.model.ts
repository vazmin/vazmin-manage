export interface IManageUser {
    id?: number;
    username?: string;
    password?: string;
    name?: string;
    phone?: string;
    email?: string;
    sendEmail?: boolean;
    wechatId?: string;
    openid?: string;
    memo?: string;
    status?: number;
    createDate?: number;
    lastVisitDate?: number;
    roleIdSet?: number[];
}
