import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const LogoutComponent = () => {
  const navigate = useNavigate();

  useEffect(function () {
    const logout = () => {
      localStorage.removeItem("token");
      localStorage.removeItem("userEmailID");
      localStorage.removeItem("userID");
      navigate(`/login`, { replace: true })
    };

    logout();
  }, [navigate])

}

export default LogoutComponent;