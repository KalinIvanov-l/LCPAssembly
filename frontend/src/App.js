import React from "react";
import {ChakraProvider, extendTheme, Grid, GridItem} from "@chakra-ui/react";

import MainSection from "./components/MainSection";

const theme = extendTheme({
  colors: {
    brand: {
      100: "#b2f5ea",
      500: "#00e4d0",
    },
  },
  fonts: {
    body: "system-ui, sans-serif",
    heading: "Georgia, serif",
    mono: "Menlo, monospace",
  },
});

const App = () => {
  return (
    <ChakraProvider theme={theme}>
      <Grid
        templateRows="repeat(2, 1fr)"
        templateColumns="repeat(5, 1fr)"
        gap={4}
        h="100vh"
      >
        <GridItem colSpan={5} bg="gray.700">
          <MainSection />
        </GridItem>
      </Grid>
    </ChakraProvider>
  );
};

export default App;
