// Define an interface for nested objects
interface NestedObject {
    [key: string]: boolean | NestedObject | null;
  }
 
  // Define the props interface for the function
  interface UpdateFormValueProps {
    dirtyFields: NestedObject;
    getValues: (keys: string[]) => any[];
  }
 
  const updateFormValue = ({ dirtyFields, getValues }: UpdateFormValueProps): Record<string, any> => {
   
 
    if(!dirtyFields || !getValues){
        console.error("Missing required parameters: getValues or dirtyFields. Returning empty object.");
            return {}
        }
   
 
    const getTrueKeys = (dirtyFields: NestedObject): string[] => {
      let result: string[] = [];
 
      for (let key in dirtyFields) {
        if (dirtyFields.hasOwnProperty(key)) {
          if (typeof dirtyFields[key] === 'object' && dirtyFields[key] !== null) {
            // Recursively call getTrueKeys for nested objects
            const nestedResult = getTrueKeys(dirtyFields[key] as NestedObject);
            // Add the nested result with a prefix to represent the key path
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
 
    const fieldValues: any[] = getValues(trueKeys);
    console.log("Modified field values:", fieldValues);
 
    const valuesObject: Record<string, any> = {};
 
    trueKeys.forEach((key: string, index: number) => {
      const value = fieldValues[index];
      const parts: string[] = key.split('.'); // Split by dot notation
 
      let current: Record<string, any> = valuesObject;
      // Handle all parts except the last one as objects
      for (let i = 0; i < parts.length - 1; i++) {
        current[parts[i]] = current[parts[i]] || {};
        current = current[parts[i]];
      }
      // Set the final value
      current[parts[parts.length - 1]] = value;
    });
 
    console.log("Nested key-value object:", valuesObject);
 
    return valuesObject;
  };
 
  export default updateFormValue;