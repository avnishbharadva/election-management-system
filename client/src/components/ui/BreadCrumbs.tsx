import { Breadcrumbs, Link, Typography } from "@mui/material";
import { useLocation, Link as RouterLink } from "react-router-dom";

export default function BreadCrumbs() {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter((x) => x);

  return (
    <Breadcrumbs sx={{ padding: "8px 16px" }} separator="›" aria-label="breadcrumb">

      {/* Home Link */}
      <Link component={RouterLink} to="/dashboard" sx={{ color: "inherit", textDecoration: "none" }}>
        Dashboard
      </Link>
      {pathnames.slice(1).map((value, index) => {
        const to = `/${pathnames.slice(0, index + 2).join("/")}`;
        const isLast = index === pathnames.length - 2;

        const formattedValue = value.replace(/-/g, " ").replace(/\b\w/g, (char) => char.toUpperCase());

        return isLast ? (
          <Typography
            key={to}
            color="textPrimary"
            aria-current="page"
            sx={{
              color: "#002F57",
              fontWeight: "bold",
              textTransform: "capitalize",
              padding: "6px",
            }}
          >
            {formattedValue}
          </Typography>
        ) : (
          <Link
            key={to}
            component={RouterLink}
            to={to}
            sx={{
              color: "#002F57",
              fontWeight: "bold",
              textTransform: "capitalize",
              padding: "6px",
            }}
          >
            {formattedValue}
          </Link>
        );
      })}
    </Breadcrumbs>
  );
}
