export enum ReqOperation {
  GRANT = 'GRANT',
  REVOKE = 'REVOKE'
}

export enum ReqPermission {
  READ = 'READ',
  ALL = 'ALL'
}

export interface ChangePermsRequest {
  userId: number;
  testId: number;
  operation: ReqOperation;
  permission: ReqPermission;
}

export interface ErrorDetailDto {
  message: string;
  path?: string;
}

export interface ValidationErrorDto {
  fieldErrors: ErrorDetailDto[];
  globalErrors: ErrorDetailDto[];
}
