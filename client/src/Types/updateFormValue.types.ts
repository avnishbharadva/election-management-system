export interface NestedObject {
    [key: string]: boolean | NestedObject | null;
  }
  
  export interface UpdateFormValueProps {
    dirtyFields: NestedObject;
    getValues: (keys: string[]) => any[] | {};
  }