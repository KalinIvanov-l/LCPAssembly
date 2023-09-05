import { useState } from "react";

export const useAssemblerProgram = () => {
  const [assemblerProgram, setAssemblerProgram] = useState("");
  return [assemblerProgram, setAssemblerProgram];
};

export const useListing = () => {
  const [listing, setListing] = useState("");
  return [listing, setListing];
};

export const useConsoleOutput = () => {
  const [consoleOutput, setConsoleOutput] = useState("");
  return [consoleOutput, setConsoleOutput];
};

export const useSelectedOption = () => {
  const [selectedOption, setSelectedOption] = useState("load");
  return [selectedOption, setSelectedOption];
};
