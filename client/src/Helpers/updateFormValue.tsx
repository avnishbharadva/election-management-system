  import { NestedObject, UpdateFormValueProps } from '../Types/updateFormValue.types';

  export const updateFormValue = ({ dirtyFields, getValues }: UpdateFormValueProps): Record<string, any> => {
    if (!dirtyFields || !getValues) {
      console.error("Missing required parameters: getValues or dirtyFields. Returning empty object.");
      return {};
    }
  
    // Helper function to get the keys of modified fields
    const getTrueKeys = (dirtyFields: NestedObject): string[] => {
      let result: string[] = [];
  
      for (let key in dirtyFields) {
        if (dirtyFields.hasOwnProperty(key)) {
          if (typeof dirtyFields[key] === 'object' && dirtyFields[key] !== null) {
            // Recursively call getTrueKeys for nested objects
            const nestedResult = getTrueKeys(dirtyFields[key] as NestedObject);
            result = result.concat(nestedResult.map(subKey => `${key}.${subKey}`));
          } else if (dirtyFields[key] === true) {
            // If the value is true, add the key to the result
            result.push(key);
          }
        }
      }
  
      return result;
    };
  
    const trueKeys: string[] = getTrueKeys(dirtyFields);
  
    // Call getValues with the trueKeys to get the actual field values
    const fieldValues = getValues(trueKeys);
  
    // Ensure fieldValues is treated as an array (guard against non-array return types)
    if (!Array.isArray(fieldValues)) {
      console.error("getValues returned an unexpected value. Expected an array but got:", fieldValues);
      return {};
    }
  
   
  
    const valuesObject: Record<string, any> = {};
  
    trueKeys.forEach((key: string, index: number) => {
      const value = fieldValues[index];
      const parts: string[] = key.split('.');
  
      let current: Record<string, any> = valuesObject;
      // Handle all parts except the last one as objects
      for (let i = 0; i < parts.length - 1; i++) {
        current[parts[i]] = current[parts[i]] || {};
        current = current[parts[i]];
      }
      // Set the final value
      current[parts[parts.length - 1]] = value;
    });
  
  
  
    return valuesObject;
  };
  
  
  
  
  