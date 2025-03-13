import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { BrowserRouter } from "react-router-dom";
<<<<<<< HEAD
// import { Provider } from 'react-redux';
=======
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
import store from './store/app/store.ts';
import { Provider } from 'react-redux';

createRoot(document.getElementById('root')!).render(
  // <StrictMode>
      <BrowserRouter>
      <Provider store={store}> 
      <App />
    </Provider>
    </BrowserRouter>
  // </StrictMode>,
)
