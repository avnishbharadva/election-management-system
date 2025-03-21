import { Breadcrumbs, Link } from "@mui/material";
import { useLocation, Link as RouterLink } from "react-router-dom";
import { BreadCrumbsName } from "../../style/CommanStyle";

export default function BreadCrumbs() {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter((x) => x);

  return (
    <Breadcrumbs sx={{ padding: "8px 16px" }} separator="›" aria-label="breadcrumb">
      <Link component={RouterLink} to="/dashboard" sx={{ color: "inherit", textDecoration: "none" }}>
        Dashboard
      </Link>
      {pathnames.slice(1).map((value, index) => {
        const to = `/${pathnames.slice(0, index + 2).join("/")}`;
        const isLast = index === pathnames.length - 2;

        const formattedValue = value.replace(/-/g, " ").replace(/\b\w/g, (char) => char.toUpperCase());

        return isLast ? (
          <BreadCrumbsName
            key={to}
            color="textPrimary"
            aria-current="page"
          >
            {formattedValue}
          </BreadCrumbsName>
        ) : (
          <Link
            key={to}
            component={RouterLink}
            to={to}
          >
            {formattedValue}
          </Link>
        );
      })}
    </Breadcrumbs>
  );
}
