import PaymentPage from "./pages/PaymentPage.jsx";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import SuccessPage from "./pages/SuccessPage.jsx";
import CancelPage from "./pages/CancelPage.jsx";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<PaymentPage/>}/>
                <Route path="/payment/success" element={<SuccessPage/>}/>
                <Route path="/payment/cancel" element={<CancelPage/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default App
