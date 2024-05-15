"use client";

import { Button } from "@/components/ui/button";
import Link from "next/link";
import { useEffect, useState } from "react";
import { ModeToggle } from "../changeTheme";

export default function Header() {
  const [token, setToken] = useState("");

  useEffect(() => {
    setToken(localStorage.getItem("token") as string);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <nav className="fixed inset-x-0 top-0 z-50 bg-white shadow-sm dark:bg-gray-950/90">
      <div className="w-full max-w-7xl mx-auto px-4">
        <div className="flex justify-around h-14 items-center">
          <Link className="flex items-center" href="#">
            <MountainIcon />
            <span className="sr-only">Gerenciador de entregas</span>
          </Link>
          <div className="flex items-center gap-4">
            {token ? (
              <Button size="sm" variant="outline" onClick={handleLogout}>
                Logout
              </Button>
            ) : (
              <Link href={"/"}>
                <Button size="sm">Sign up</Button>
              </Link>
            )}
          </div>
          <ModeToggle />
        </div>
      </div>
    </nav>
  );
}

function MountainIcon() {
  return (
    <svg
      className="h-6 w-6"
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="m8 3 4 8 5-5 5 15H2L8 3z" />
    </svg>
  );
}
