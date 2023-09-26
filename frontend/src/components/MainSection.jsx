import {
  Box,
  Button,
  Textarea,
  Flex,
  Text,
  Select,
  ChakraProvider,
} from "@chakra-ui/react";
import {
  useAssemblerProgram,
  useListing,
  useConsoleOutput,
  useSelectedOption,
} from "../state";
import { handleLoadFile } from "../api/handleLoadFile";
import { handleAssemble } from "../api/handleAssemble";

const MainSection = () => {
  const [assemblerProgram, setAssemblerProgram] = useAssemblerProgram();
  const [listing, setListing] = useListing();
  const [consoleOutput, setConsoleOutput] = useConsoleOutput();
  const [selectedOption, setSelectedOption] = useSelectedOption();

  const handleActionSelect = (e) => {
    setSelectedOption(e.target.value);
  };

  const handleDropdownAction = async () => {
    switch (selectedOption) {
      case "load":
        await handleLoadFile(setAssemblerProgram, setConsoleOutput);
        break;
      case "assemble":
        await handleAssemble(assemblerProgram, setListing, setConsoleOutput);
        break;
      default:
        break;
    }
  };

  return (
    <ChakraProvider>
      <Box
        minHeight="100vh"
        display="flex"
        flexDirection="column"
        justifyContent="stretch"
      >
        <Box flex={4} bg="black" color="white" p={4} justifyContent="flex-end">
          <Flex p={1}>
            <Select
                onMouseLeave={(e) => e.target.size = 1}
                onChange={(event) => handleActionSelect(event)}
                value={selectedOption}
                cursor="pointer"
            >
              <option value="load">Load</option>
              <option value="assemble">Assemble</option>
            </Select>
            <Button colorScheme="teal" onClick={handleDropdownAction}>
              Execute
            </Button>
          </Flex>
        </Box>
        <Flex flex={1} bg="black" color="white" p={4}>
          <Box flex={1} mr={4}>
            <Text mb={5} fontSize="xl" fontWeight="bold">
              Assembler Program
            </Text>
            <Textarea
              value={assemblerProgram}
              onChange={(e) => setAssemblerProgram(e.target.value)}
              rows={10}
              placeholder="Enter the assembler program"
              bg="white"
              color="black"
              borderRadius="md"
              resize="none"
            />
          </Box>
          <Box flex={1}>
            <Text mb={5} fontSize="xl" fontWeight="bold">
              Listing
            </Text>
            <Textarea
              value={listing}
              onChange={(e) => setListing(e.target.value)}
              rows={10}
              placeholder="Generated listing"
              bg="white"
              color="black"
              borderRadius="md"
              resize="none"
            />
          </Box>
        </Flex>

        <Box flex={1} bg="gray.800" color="white" p={4}>
          <Text mb={5} fontSize="xl" fontWeight="bold">
            Console
          </Text>
          <Textarea
            value={consoleOutput}
            onChange={(e) => setConsoleOutput(e.target.value)}
            rows={5}
            placeholder="Console output"
            bg="white"
            color="black"
            borderRadius="md"
            resize="none"
          />
        </Box>
        <Box
          bgGradient="linear(to-l, teal.500, teal.500)"
          p={4}
          borderRadius="lg"
          boxShadow="xl"
          textAlign="center"
        >
          <Text fontSize="2xl" fontWeight="bold" color="white">
            LCPAssembler
          </Text>
        </Box>
      </Box>
    </ChakraProvider>
  );
};

export default MainSection;
