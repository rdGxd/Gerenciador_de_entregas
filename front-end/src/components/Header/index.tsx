"use client";

import { Button } from "@/components/ui/button";
import Link from "next/link";
import { ModeToggle } from "../changeTheme";

export default function Header() {
  return (
    <>
      <nav className="fixed inset-x-0 top-0 z-50 bg-white shadow-sm dark:bg-gray-950/90">
        <div className="w-full max-w-7xl mx-auto px-4">
          <div className="flex justify-around h-14 items-center">
            <Link className="flex items-center" href="#">
              <MountainIcon className="h-6 w-6" />
              <span className="sr-only">Gerenciador de entregas</span>
            </Link>
            <div className="flex items-center gap-4">
              <Link href={"/login"}>
                <Button size="sm" variant="outline">
                  Sign in
                </Button>
              </Link>
              <Link href={"/register"}>
                <Button size="sm">Sign up</Button>
              </Link>
            </div>
            <ModeToggle />
          </div>
        </div>
      </nav>
    </>
  );
}

function MountainIcon(props: any) {
  return (
    <svg
      {...props}
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
