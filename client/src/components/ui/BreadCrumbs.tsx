import { Breadcrumbs, Link, Typography } from "@mui/material";
import { useLocation, Link as RouterLink } from "react-router-dom";

export default function BreadCrumbs() {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter((x) => x);

  return (
    <Breadcrumbs sx={{ padding: "8px 16px" }} separator="›" aria-label="breadcrumb">
      <Link component={RouterLink} to="/" sx={{ color: "inherit", textDecoration: "none" }}>
        Dashboard
      </Link>
      {pathnames.map((value, index) => {
        const to = `/${pathnames.slice(0, index + 1).join("/")}`;
        const isLast = index === pathnames.length - 1;
        return isLast ? (
          <Typography 
            key={to} 
            color="textPrimary"
            sx={{
              // backgroundColor: "rgba(0, 48, 87, 0.1)", // Light blue background
              color: "#002F57", // Dark blue text
              borderRadius: "20px", // Rounded edges
              fontWeight: "bold",
              textTransform: "none", // Keep text case normal
              padding: "6px",
              // "&:hover": {
              //   backgroundColor: "rgba(0, 48, 87, 0.2)", // Slightly darker hover effect
              // },
            }}
            >{value}</Typography>
        ) : (
          <Link 
            key={to} 
            component={RouterLink} 
            to={to} 
            sx={{
              // backgroundColor: "rgba(0, 48, 87, 0.1)", // Light blue background
              color: "#002F57", // Dark blue text
              borderRadius: "20px", // Rounded edges
              fontWeight: "bold",
              textTransform: "none", // Keep text case normal
              padding: "6px",
              // "&:hover": {
              //   backgroundColor: "rgba(0, 48, 87, 0.2)", 
              // },
            }}>
            {value}
          </Link>
        );
      })}
    </Breadcrumbs>
  );
}
