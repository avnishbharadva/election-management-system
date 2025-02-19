
import { useState, useEffect } from 'react';

const useQuery = (apiFunction: (params: any) => Promise<any>, params: any) => {
  const [data, setData] = useState<any>([]);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setLoading] = useState<boolean>(true);

  const getData = async () => {
    try {
      setLoading(true);
      const result = await apiFunction(params)
      setData(result);  
    } catch (err) {
      setError('Failed to fetch data');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    getData(); 
  }, [apiFunction, params])

  return { data, error, isLoading };
};

export default useQuery;